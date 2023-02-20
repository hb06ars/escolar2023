package com.brandaoti.escolar.dtos;

import java.time.LocalDate;
import java.util.List;

import com.brandaoti.escolar.domain.Turma;
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
	
	public TurmaDTO(Turma obj) {
		super();
		this.id = obj.getId();
		this.serie = obj.getSerie();
		this.turma = obj.getTurma();
		this.sala = obj.getSala();
		this.periodo = obj.getPeriodo();
		this.dataAtualizacao = obj.getDataAtualizacao();
		this.alunos = obj.getAlunos();
	}

	
}
