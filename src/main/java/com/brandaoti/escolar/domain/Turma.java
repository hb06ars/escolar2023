package com.brandaoti.escolar.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.brandaoti.escolar.domain.enums.EnumPeriodo;
import com.brandaoti.escolar.dtos.TurmaDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "turma")
public class Turma implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	
	@Column(unique = false)
	protected Integer serie;

	@Column(unique = false)
	protected String turma;
	
	@Column(unique = false)
	protected Integer sala;
	
	@Column(unique = false)
	protected EnumPeriodo periodo;

	@OneToMany
	protected List<Usuario> alunos;
	
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column
	protected LocalDate dataAtualizacao = LocalDate.now();

	
	//Período Atual
	public EnumPeriodo periodoAtual() {
		LocalTime agora = LocalTime.now();
		if(agora.compareTo(EnumPeriodo.MADRUGADA.getInicio()) >= 0 && agora.compareTo(EnumPeriodo.MADRUGADA.getFim()) < 0  )
			return EnumPeriodo.MADRUGADA;
		
		if(agora.compareTo(EnumPeriodo.MANHA.getInicio()) >= 0 && agora.compareTo(EnumPeriodo.MANHA.getFim()) < 0  )
			return EnumPeriodo.MANHA;
		
		if(agora.compareTo(EnumPeriodo.TARDE.getInicio()) >= 0 && agora.compareTo(EnumPeriodo.TARDE.getFim()) < 0  )
			return EnumPeriodo.TARDE;
		
		if(agora.compareTo(EnumPeriodo.NOITE.getInicio()) >= 0 && agora.compareTo(EnumPeriodo.NOITE.getFim()) < 0  )
			return EnumPeriodo.NOITE;
		
		return null;
	}
	
	//Construtor da classe sem parâmetros
	public Turma() {
		super();
	}
	
	//Construtor da classe com parâmetros
	public Turma(Integer id, Integer serie, String turma, Integer sala, EnumPeriodo periodo, LocalDate dataAtualizacao, List<Usuario> alunos) {
		super();
		this.id = id;
		this.serie = serie;
		this.turma = turma;
		this.sala = sala;
		this.periodo = periodo;
		this.dataAtualizacao = dataAtualizacao;
		this.alunos = alunos;
	}

	public Turma(TurmaDTO obj) {
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
