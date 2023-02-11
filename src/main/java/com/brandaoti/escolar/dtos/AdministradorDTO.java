package com.brandaoti.escolar.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.brandaoti.escolar.domain.Administrador;
import com.brandaoti.escolar.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;

//DTO padrão de Segurança para não retornar um objeto quando chamar a API e sim esse DTO.
public class AdministradorDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	@NotNull(message = "O campo nome é requerido")
	protected String nome;
	@NotNull(message = "O campo CPF é requerido")
	protected String cpf;
	@NotNull(message = "O campo email é requerido")
	protected String email;
	@NotNull(message = "O campo senha é requerido")
	protected String senha;
	protected Set<Integer> perfis = new HashSet<>(); // Não permite 2 valores iguais na lista.
	
	@JsonFormat(pattern="dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();

	public AdministradorDTO() {
		super();
		addPerfil(Perfil.ADMINISTRADOR);
	}

	public AdministradorDTO(Administrador obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = obj.getDataCriacao();
		addPerfil(Perfil.ADMINISTRADOR);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	
	

}
