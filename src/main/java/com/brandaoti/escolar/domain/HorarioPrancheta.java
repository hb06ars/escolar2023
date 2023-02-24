package com.brandaoti.escolar.domain;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorarioPrancheta {
	
	@Column(name="horario")
	private LocalTime horario;
	
	@Column(name="salaProfessor")
	private List<Horario> salaProfessor;
	

}
