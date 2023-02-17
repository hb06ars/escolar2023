package com.brandaoti.escolar.dtos;

import java.io.Serializable;

import com.brandaoti.escolar.domain.Perfil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//DTO padrão de Segurança para não retornar um objeto quando chamar a API e sim esse DTO.
public class PerfilDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer codigo;
	protected String role;
	protected String descricao;
	
	public PerfilDTO() {
		super();
	}

	public PerfilDTO(Perfil obj) {
		super();
		this.id = obj.getId();
		this.codigo = obj.getCodigo();
		this.role = obj.getRole();
		this.descricao = obj.getDescricao(); 
	}


}
