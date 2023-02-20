package com.brandaoti.escolar.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.brandaoti.escolar.domain.enums.EnumPeriodo;
import com.brandaoti.escolar.domain.enums.EnumSemana;
import com.brandaoti.escolar.dtos.AulaDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "aula")
public class Aula implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	
	@Column(unique = false)
	protected EnumSemana diaDaSemana;

	@Column(unique = false)
	protected EnumPeriodo periodo;
	
	@ManyToOne
	protected Disciplina disciplina;
	
	@ManyToOne
	protected Usuario professor;
	
	@ManyToOne
	protected Usuario professorSubstituto;
	
	@Column(unique = false)
	protected LocalTime inicioAula = LocalTime.of(00, 00, 00);
	
	@Column(unique = false)
	protected LocalTime fimAula = LocalTime.of(00, 00, 00);
	

	@ManyToOne
	protected Turma turma;
	
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column
	protected LocalDate dataAtualizacao = LocalDate.now();
	
	//Construtor da classe sem parâmetros
	public Aula() {
		super();
	}
	
	//Construtor da classe com parâmetros
	public Aula(Integer id, EnumSemana diaDaSemana, EnumPeriodo periodo, Disciplina disciplina, LocalDate dataAtualizacao, Turma turma, Usuario professor, Usuario professorSubstituto, LocalTime inicioAula, LocalTime fimAula) {
		super();
		this.id = id;
		this.diaDaSemana = diaDaSemana;
		this.periodo = periodo;
		this.disciplina = disciplina;
		this.turma = turma;
		this.dataAtualizacao = dataAtualizacao;
		this.inicioAula = inicioAula;
		this.fimAula = fimAula;
		this.professor = professor; 
		this.professorSubstituto = professorSubstituto;
	}

	public Aula(AulaDTO obj) {
		super();
		this.id = obj.getId();
		this.diaDaSemana = obj.getDiaDaSemana();
		this.periodo = obj.getPeriodo();
		this.disciplina = obj.getDisciplina();
		this.turma = obj.getTurma();
		this.dataAtualizacao = obj.getDataAtualizacao();
		this.inicioAula = obj.getInicioAula();
		this.fimAula = obj.getFimAula();
		this.professor = obj.getProfessor();
		this.professorSubstituto = obj.getProfessorSubstituto();

		
	}
}
