package com.brandaoti.escolar.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;

import com.brandaoti.escolar.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public abstract class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	
	@NotNull(message = "Digite um nome.")
	@NotEmpty(message = "Digite um nome.")
	protected String nome;
	
	@CPF(message = "CPF inválido.")
	@Column(unique = true)
	protected String cpf;
	
	@Email(message = "Digite um email válido.")
	@Column(unique = true)
	protected String email;
	
	@NotNull(message = "Digite uma senha.")
	@NotEmpty(message = "Digite uma senha.")
	protected String senha;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	protected Set<Integer> perfis = new HashSet<>(); //Não permite 2 valores iguais na lista.
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();
	
	@Pattern(regexp = "(d{2})\\d{5}-\\d{3}")
    protected String telefone;
	

	//Construtor da classe sem parâmetros
	public Pessoa() {
		super();
		addPerfil(Perfil.VISITANTE); // Aqui todo usuario criado vai ter o Perfil de VISITANTE pelo menos.
	}
	
	//Construtor da classe com parâmetros
	public Pessoa(Integer id, String nome, String cpf, String email, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		addPerfil(Perfil.VISITANTE); // Aqui todo usuario criado vai ter o Perfil de VISITANTE pelo menos.
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
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
		Pessoa other = (Pessoa) obj;
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

}
