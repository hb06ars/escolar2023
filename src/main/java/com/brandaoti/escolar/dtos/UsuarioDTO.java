package com.brandaoti.escolar.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.brandaoti.escolar.domain.Usuario;
import com.brandaoti.escolar.domain.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//DTO padrão de Segurança para não retornar um objeto quando chamar a API e sim esse DTO.
public class UsuarioDTO implements Serializable{
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
	
	protected Perfil perfil;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();

	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(Usuario obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfil = obj.getPerfil();
		this.telefone = obj.getTelefone();
		this.dataCriacao = obj.getDataCriacao();
		this.perfil = obj.getPerfil();
	}
	

}
