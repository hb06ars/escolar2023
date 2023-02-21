package com.brandaoti.escolar.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.brandaoti.escolar.dtos.DisciplinaDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "disciplina")
public class Disciplina implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	
	@Column(unique = false)
	protected Integer codigo;
	
	@Column(unique = true)
	protected String nomeDisciplina;
	
	//Construtor da classe sem parâmetros
	public Disciplina() {
		super();
	}
	
	//Construtor da classe com parâmetros
	public Disciplina(Integer id, Integer codigo, String nomeDisciplina) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nomeDisciplina = nomeDisciplina;
	}

	public Disciplina(DisciplinaDTO obj) {
		super();
		this.id = obj.getId();
		this.codigo = obj.getCodigo();
		this.nomeDisciplina = obj.getNomeDisciplina();
	}
	
}
