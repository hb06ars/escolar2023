package com.brandaoti.escolar.domain.enums;

import lombok.Getter;

@Getter
public enum EnumPerfil {

	ADMINISTRADOR(0, "ROLE_ADMINISTRADOR", "ADMINISTRADOR"), 
	PROFESSOR(1, "ROLE_PROFESSOR", "PROFESSOR"), 
	ALUNO(2, "ROLE_ALUNO", "ALUNO"), 
	VISITANTE(3, "ROLE_VISITANTE", "VISITANTE");
	
	private Integer codigo;
	private String role;
	private String descricao;
	
	private EnumPerfil(Integer codigo, String role, String descricao) {
		this.codigo = codigo;
		this.role = role;
		this.descricao = descricao;
	}

	public static EnumPerfil toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(EnumPerfil x : EnumPerfil.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Perfil inválido");
	}
}
