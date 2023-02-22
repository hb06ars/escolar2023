package com.brandaoti.escolar.domain.enums;

import lombok.Getter;

@Getter
public enum EnumSemana {

	DOMINGO	(0, "DOMINGO"),
	SEGUNDA	(1, "SEGUNDA"),
	TERCA	(2, "TERCA"	),
	QUARTA	(3, "QUARTA"),
	QUINTA	(4, "QUINTA"),
	SEXTA	(5, "SEXTA"),
	SABADO	(6, "SABADO");
	
	private Integer codigo;
	private String descricao;
	
	private EnumSemana(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public static EnumSemana toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(EnumSemana x : EnumSemana.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Dia da semana inválido");
	}
}
