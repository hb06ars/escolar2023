package com.brandaoti.escolar.domain;

import java.util.stream.Collectors;

import javax.persistence.Entity;

import com.brandaoti.escolar.domain.enums.Perfil;
import com.brandaoti.escolar.dtos.AdministradorDTO;

@Entity
public class Administrador extends Pessoa {
	private static final long serialVersionUID = 1L;

	public Administrador() {
		super();
		addPerfil(Perfil.ADMINISTRADOR);
	}

	public Administrador(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		addPerfil(Perfil.PROFESSOR);
		addPerfil(Perfil.ADMINISTRADOR);
	}
	
	public Administrador(AdministradorDTO obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = obj.getDataCriacao();
	}

	
}
