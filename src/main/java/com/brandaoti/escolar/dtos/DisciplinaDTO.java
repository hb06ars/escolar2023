package com.brandaoti.escolar.dtos;

import com.brandaoti.escolar.domain.Disciplina;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisciplinaDTO {
	
	protected Integer id;
	protected Integer codigo;
	protected String nomeDisciplina;
	
	public DisciplinaDTO() {
		super();
	}
	
	public DisciplinaDTO(Disciplina obj) {
		super();
		this.id = obj.getId();
		this.codigo = obj.getCodigo();
		this.nomeDisciplina = obj.getNomeDisciplina();
	}
}
