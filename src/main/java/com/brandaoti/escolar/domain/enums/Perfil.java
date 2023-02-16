package com.brandaoti.escolar.domain.enums;

import lombok.Getter;

@Getter
public enum Perfil {

	ADMINISTRADOR(0, "ROLE_ADMINISTRADOR"), PROFESSOR(1, "ROLE_PROFESSOR"), ALUNO(2, "ROLE_ALUNO"), VISITANTE(3, "ROLE_VISITANTE");
	
	private Integer codigo;
	private String descricao;
	
	private Perfil(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public static Perfil toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(Perfil x : Perfil.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Perfil inválido");
	}
}
