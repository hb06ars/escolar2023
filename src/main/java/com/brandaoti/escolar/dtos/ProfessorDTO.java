package com.brandaoti.escolar.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.brandaoti.escolar.domain.Professor;
import com.brandaoti.escolar.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//DTO padrão de Segurança para não retornar um objeto quando chamar a API e sim esse DTO.
public class ProfessorDTO implements Serializable{
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
	@NotNull(message = "O campo telefone é requerido")
	protected String telefone;
	@JsonFormat(pattern="dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();

	public ProfessorDTO() {
		super();
		addPerfil(Perfil.PROFESSOR);
	}

	public ProfessorDTO(Professor obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = obj.getDataCriacao();
		this.telefone = obj.getTelefone();
		addPerfil(Perfil.PROFESSOR);
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	
	

}
