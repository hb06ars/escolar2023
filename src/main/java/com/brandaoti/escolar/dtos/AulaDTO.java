package com.brandaoti.escolar.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import com.brandaoti.escolar.domain.Aula;
import com.brandaoti.escolar.domain.Disciplina;
import com.brandaoti.escolar.domain.Turma;
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
	protected Turma turma;
	protected LocalDate dataAtualizacao;
	protected LocalTime inicioAula;
	protected LocalTime fimAula;
	protected Usuario professor;
	protected Usuario professorSubstituto;
	
	public AulaDTO() {
		super();
	}
	
	
	public AulaDTO(Aula obj) {
		super();
		this.id = obj.getId();
		this.diaDaSemana = obj.getDiaDaSemana();
		this.periodo = obj.getPeriodo();
		this.disciplina = obj.getDisciplina();
		this.turma = removerDadosTurma(obj.getTurma());
		this.dataAtualizacao = obj.getDataAtualizacao();
		this.inicioAula = obj.getInicioAula();
		this.fimAula = obj.getFimAula();
		if(this.professor != null)
			this.professor = removerDadosProfessores(obj.getProfessor());
		if(this.professorSubstituto != null)
			this.professorSubstituto = removerDadosProfessores(obj.getProfessorSubstituto());
	}
	
	
	public Turma removerDadosTurma(Turma obj) {
		obj.getAlunos().stream().forEach(o -> {
			o.setCpf(null);
			o.setEmail(null);
			o.setSenha(null);
			o.setPerfil(null);
			o.setDataCriacao(null);
			o.setTelefone(null);
		});
		return obj;
	}
	
	public Usuario removerDadosProfessores(Usuario obj) {
		obj.setCpf(null);
		obj.setEmail(null);
		obj.setSenha(null);
		obj.setPerfil(null);
		obj.setDataCriacao(null);
		obj.setTelefone(null);
		return obj;
	}
	
}
