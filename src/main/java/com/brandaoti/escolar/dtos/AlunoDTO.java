package com.brandaoti.escolar.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.brandaoti.escolar.domain.Aluno;
import com.brandaoti.escolar.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//DTO padrão de Segurança para não retornar um objeto quando chamar a API e sim esse DTO.
public class AlunoDTO implements Serializable{
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
	@NotNull(message = "O campo telefone é requerido")
	protected String telefone;
	
	protected Set<Integer> perfis = new HashSet<>(); // Não permite 2 valores iguais na lista.
	
	@JsonFormat(pattern="dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();

	public AlunoDTO() {
		super();
		addPerfil(Perfil.ALUNO);
	}

	public AlunoDTO(Aluno obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = obj.getDataCriacao();
		this.telefone = obj.getTelefone();
		addPerfil(Perfil.ALUNO);
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}

}
