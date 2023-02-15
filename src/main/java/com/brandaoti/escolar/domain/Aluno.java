package com.brandaoti.escolar.domain;

import java.util.stream.Collectors;

import javax.persistence.Entity;

import com.brandaoti.escolar.domain.enums.Perfil;
import com.brandaoti.escolar.dtos.AlunoDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Aluno extends Pessoa {
	private static final long serialVersionUID = 1L;

	public Aluno() {
		super();
		addPerfil(Perfil.ALUNO);
	}

	public Aluno(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		addPerfil(Perfil.ALUNO);
	}
	
	public Aluno(AlunoDTO obj) {
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
