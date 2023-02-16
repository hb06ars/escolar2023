package com.brandaoti.escolar.domain;

import java.util.stream.Collectors;

import javax.persistence.Entity;

import com.brandaoti.escolar.domain.enums.Perfil;
import com.brandaoti.escolar.dtos.AdministradorDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Administrador extends Pessoa {
	private static final long serialVersionUID = 1L;

	public Administrador() {
		super();
		addPerfil(Perfil.ADMINISTRADOR);
	}

	public Administrador(Integer id, String nome, String cpf, String email, String senha, String telefone) {
		super(id, nome, cpf, email, senha, telefone);
		addPerfil(Perfil.ADMINISTRADOR);
	}
	
	public Administrador(AdministradorDTO obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.telefone = obj.getTelefone();
		this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = obj.getDataCriacao();
	}

	
}
