package com.banking.service.http;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.banking.domain.entity.Agencia;
import com.banking.domain.enums.SituacaoCadastral;
import com.banking.domain.http.AgenciaHttp;
import com.banking.domain.repository.AgenciaRepository;
import com.banking.exceptions.AgenciaNaoAtivaOuNaoEncontradaException;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AgenciaHttpService {
	
	@RestClient
	private SituacaoCadastralHttpService situacaoCadastralHttpService;

	private final AgenciaRepository agenciaRepository;

	AgenciaHttpService(AgenciaRepository agenciaRepository) {
		this.agenciaRepository = agenciaRepository;
	}

	public void cadastrar(Agencia agencia) {
		AgenciaHttp agenciaHttp = situacaoCadastralHttpService.buscarPorCnpj(agencia.getCnpj());

		if (
			agenciaHttp != null && 
			agenciaHttp.getSituacaoCadastral().equals(SituacaoCadastral.ATIVO)
		) {
			this.agenciaRepository.persist(agencia);
		} else {
			throw new AgenciaNaoAtivaOuNaoEncontradaException();
		}
	}

	public Agencia buscarPorId(Long id) {
		return this.agenciaRepository.findById(id);
	}

	public void deletar(Long id) {
		this.agenciaRepository.deleteById(id);;
	}

	public void alterar(Agencia agencia) {
		this.agenciaRepository.update(
			"nome = ?1, razaoSocial = ?2, cnpj = ?3 where id = ?4",
			agencia.getNome(), agencia.getRazaoSocial(), agencia.getCnpj(), agencia.getId()
		);
	}

}
