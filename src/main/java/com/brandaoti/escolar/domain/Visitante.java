package com.brandaoti.escolar.domain;

import java.util.stream.Collectors;

import javax.persistence.Entity;

import com.brandaoti.escolar.dtos.VisitanteDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Visitante extends Pessoa {
	private static final long serialVersionUID = 1L;

	public Visitante() {
		super();
	}

	public Visitante(Integer id, String nome, String cpf, String email, String senha, String telefone) {
		super(id, nome, cpf, email, senha, telefone);
	}
	
	public Visitante(VisitanteDTO obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.telefone = obj.getTelefone();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = obj.getDataCriacao();
	}

	
}
