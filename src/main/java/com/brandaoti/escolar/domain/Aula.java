package com.brandaoti.escolar.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	
	@NotNull(message = "Digite o dia da Semana.")
	@NotEmpty(message = "Digite o dia da Semana.")
	@Column(unique = false)
	protected EnumSemana diaDaSemana;

	@NotNull(message = "Digite o período.")
	@NotEmpty(message = "Digite o período.")
	@Column(unique = false)
	protected EnumPeriodo periodo;
	
	@NotNull(message = "Digite a disciplina.")
	@NotEmpty(message = "Digite a disciplina.")
	@Column(unique = false)
	protected Disciplina disciplina;

	@OneToMany
	@Column(unique = false)
	protected List<Usuario> alunos;
	
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column
	protected LocalDate dataAtualizacao = LocalDate.now();
	
	//Construtor da classe sem parâmetros
	public Aula() {
		super();
	}
	
	//Construtor da classe com parâmetros
	public Aula(Integer id, EnumSemana diaDaSemana, EnumPeriodo periodo, Disciplina disciplina, LocalDate dataAtualizacao, List<Usuario> alunos) {
		super();
		this.id = id;
		this.diaDaSemana = diaDaSemana;
		this.periodo = periodo;
		this.disciplina = disciplina;
		this.alunos = alunos;
		this.dataAtualizacao = dataAtualizacao;
	}

	public Aula(AulaDTO obj) {
		super();
		this.id = obj.getId();
		this.diaDaSemana = obj.getDiaDaSemana();
		this.periodo = obj.getPeriodo();
		this.disciplina = obj.getDisciplina();
		this.alunos = obj.getAlunos();
		this.dataAtualizacao = obj.getDataAtualizacao();
	}
}
