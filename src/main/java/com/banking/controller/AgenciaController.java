package com.banking.controller;

import org.jboss.resteasy.reactive.RestResponse;

import com.banking.domain.entity.Agencia;
import com.banking.service.http.AgenciaHttpService;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

@Path("/agencias")
public class AgenciaController {
	
	private AgenciaHttpService agenciaHttpService;

	AgenciaController (AgenciaHttpService agenciaHttpService) {
		this.agenciaHttpService = agenciaHttpService;
	}

	@POST
	public RestResponse<Void> cadastrar(Agencia agencia, @Context UriInfo uriInfo) {
		this.agenciaHttpService.cadastrar(agencia);
		return RestResponse.created(uriInfo.getAbsolutePath());
	}

	@GET
	@Path("{id}")
	public RestResponse<Agencia> buscarPorId(Integer id) {
		Agencia agencia = this.agenciaHttpService.buscarPorId(id);
		return RestResponse.ok(agencia);
	}

	@DELETE
	@Path("{id}")
	public RestResponse<Void> deletar(Integer id) {
		this.agenciaHttpService.deletar(id);
		return RestResponse.ok();
	}

	@PUT
	public RestResponse<Void> alterar(Agencia agencia) {
		this.agenciaHttpService.alterar(agencia);
		return RestResponse.ok();
	}

}
