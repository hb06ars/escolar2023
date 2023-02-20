package com.brandaoti.escolar.dtos;

import java.time.LocalDate;
import java.util.List;

import com.brandaoti.escolar.domain.Disciplina;
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
	protected List<Usuario> alunos;
	protected LocalDate dataAtualizacao;
	
}
