package com.banking.service.http;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.banking.domain.entity.Agencia;
import com.banking.domain.enums.SituacaoCadastral;
import com.banking.domain.http.AgenciaHttp;
import com.banking.exceptions.AgenciaNaoAtivaOuNaoEncontradaException;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AgenciaHttpService {
	
	@RestClient
	private SituacaoCadastralHttpService situacaoCadastralHttpService;

	private List<Agencia> agencias = new ArrayList<>();

	public void cadastrar(Agencia agencia) {
		AgenciaHttp agenciaHttp = situacaoCadastralHttpService.buscarPorCnpj(agencia.getCnpj());

		if (
			agenciaHttp != null && 
			agenciaHttp.getSituacaoCadastral().equals(SituacaoCadastral.ATIVO)
		) {
			agencias.add(agencia);
		} else {
			throw new AgenciaNaoAtivaOuNaoEncontradaException();
		}
	}

	public Agencia buscarPorId(Integer id) {
		return agencias.stream().
			filter(agencia -> agencia.getId().equals(id)).toList().getFirst();
	}

	public void deletar(Integer id) {
		agencias.removeIf(agencia -> agencia.getId().equals(id));
	}

	public void alterar(Agencia agencia) {
		deletar(agencia.getId());
		cadastrar(agencia);
	}

}
