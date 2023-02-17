package com.brandaoti.escolar.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.brandaoti.escolar.dtos.PerfilDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "perfil")
public class Perfil implements GrantedAuthority , Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private Integer codigo;
	private String role;
	private String descricao;
	
	public Perfil(Integer id, Integer codigo, String role, String descricao) {
		this.id = id;
		this.codigo = codigo;
		this.role = role;
		this.descricao = descricao;
	}

	public Perfil() {
		super();
	}
	
	public Perfil(PerfilDTO obj) {
		super();
		this.id = obj.getId();
		this.codigo = obj.getCodigo();
		this.role = obj.getRole();
		this.descricao = obj.getDescricao();
	}

	@Override
	public String getAuthority() {
		return  this.role;
	}


}
