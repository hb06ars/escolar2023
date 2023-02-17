package com.brandaoti.escolar.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.brandaoti.escolar.domain.enums.EnumPerfil;
import com.brandaoti.escolar.dtos.UsuarioDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	
	@NotNull(message = "Digite um nome.")
	@NotEmpty(message = "Digite um nome.")
	@Column
	protected String nome;
	
	@CPF(message = "CPF inválido.")
	@Column(unique = true)
	protected String cpf;
	
	@Email(message = "Digite um email válido.")
	@Column(unique = true)
	protected String email;
	
	@NotNull(message = "Digite uma senha.")
	@NotEmpty(message = "Digite uma senha.")
	@Column
	protected String senha;
	
	@ManyToOne
	protected Perfil perfil = new Perfil(null, EnumPerfil.VISITANTE.getCodigo(), EnumPerfil.VISITANTE.getRole(), EnumPerfil.VISITANTE.getDescricao()); //Não permite 2 valores iguais na lista.
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column
	protected LocalDate dataCriacao = LocalDate.now();
	
	@Column
	protected String telefone;
	

	//Construtor da classe sem parâmetros
	public Usuario() {
		super();
		//addPerfil(EnumPerfil.VISITANTE); // Aqui todo usuario criado vai ter o Perfil de VISITANTE pelo menos.
	}
	
	
	
	
	//Construtor da classe com parâmetros
	public Usuario(Integer id, String nome, String cpf, String email, String senha, String telefone, Perfil perfis) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		this.telefone = telefone;
		this.perfil = perfis;
		//this.setPerfis(EnumPerfil.VISITANTE.getDescricao()); // Aqui todo usuario criado vai ter o Perfil de VISITANTE pelo menos.
	}

	//Generate Hashcode e equals Serve para fazer comparação de objeto por valor dele, exemplo CPF ou ID.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	//Generate Hashcode e equals Serve para fazer comparação de objeto por valor dele, exemplo CPF ou ID.
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}




	public Usuario(UsuarioDTO obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfil = obj.getPerfil();
		this.dataCriacao = obj.getDataCriacao();
		this.telefone = obj.getTelefone();
	}
}
