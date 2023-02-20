package com.brandaoti.escolar.dtos;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisciplinaDTO {
	
	protected Integer id;
	protected Integer codigo;
	protected String nomeDisciplina;
	protected LocalDate dataAtualizacao;
	
}
