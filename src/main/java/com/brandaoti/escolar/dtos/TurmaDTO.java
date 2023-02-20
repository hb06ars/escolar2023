package com.brandaoti.escolar.dtos;

import java.time.LocalDate;
import java.util.List;

import com.brandaoti.escolar.domain.Usuario;
import com.brandaoti.escolar.domain.enums.EnumPeriodo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TurmaDTO {
	
	protected Integer id;
	protected Integer serie;
	protected String turma;
	protected Integer sala;
	protected EnumPeriodo periodo;
	protected LocalDate dataAtualizacao;
	protected List<Usuario> alunos;
	
}
