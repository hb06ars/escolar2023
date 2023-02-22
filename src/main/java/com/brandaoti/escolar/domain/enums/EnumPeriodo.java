package com.brandaoti.escolar.domain.enums;

import java.time.LocalTime;

import lombok.Getter;

@Getter
public enum EnumPeriodo {

	MADRUGADA	(0, "MADRUGADA", 	LocalTime.of(00,00,00), LocalTime.of(05,59,59)),
	MANHA		(1, "MANHA", 		LocalTime.of(06,00,00), LocalTime.of(11,59,59)),
	TARDE		(2, "TARDE", 		LocalTime.of(12,00,00), LocalTime.of(17,59,59)),
	NOITE		(3, "NOITE", 		LocalTime.of(18,00,00), LocalTime.of(23,59,59));
	
	private Integer codigo;
	private String descricao;
	private LocalTime inicio;
	private LocalTime fim;
	

	private EnumPeriodo(Integer codigo, String descricao, LocalTime inicio, LocalTime fim) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.inicio = inicio;
		this.fim = fim;
	}

	public static EnumPeriodo toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(EnumPeriodo x : EnumPeriodo.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Período inválido");
	}
}
