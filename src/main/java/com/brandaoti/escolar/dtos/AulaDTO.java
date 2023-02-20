package com.brandaoti.escolar.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import com.brandaoti.escolar.domain.Disciplina;
import com.brandaoti.escolar.domain.Turma;
import com.brandaoti.escolar.domain.Usuario;
import com.brandaoti.escolar.domain.enums.EnumPeriodo;
import com.brandaoti.escolar.domain.enums.EnumSemana;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AulaDTO {
	
	protected Integer id;
	protected EnumSemana diaDaSemana;
	protected EnumPeriodo periodo;
	protected Disciplina disciplina;
	protected Turma turma;
	protected LocalDate dataAtualizacao;
	protected LocalTime inicioAula;
	protected LocalTime fimAula;
	protected Usuario professor;
	protected Usuario professorSubstituto;
	
}
