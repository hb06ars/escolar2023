package com.brandaoti.escolar.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Horario {
	
	@Column(name="idAula")
	private Integer idAula;
	
	@Column(name="codigo")
	private Integer codigo;
	
	@Column(name="sala")
	private Integer sala;
	
	@Column(name="inicio")
	private LocalTime inicio;
	
	@Column(name="fim")
	private LocalTime fim;
	
	@Column(name="nomeDaDisciplina")
	private String nomeDaDisciplina;
	
	@Column(name="professor")
	private String professor;
	
	@Column(name="professorSubstituto")
	private String professorSubstituto;
	
	
	
	

}
