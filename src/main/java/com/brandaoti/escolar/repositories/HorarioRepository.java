package com.brandaoti.escolar.repositories;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.brandaoti.escolar.domain.Horario;
import com.brandaoti.escolar.domain.enums.EnumPeriodo;
import com.brandaoti.escolar.domain.enums.EnumSemana;

@Repository
public class HorarioRepository {
	
	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Horario> buscarHorario(Integer diaDaSemana, Integer periodo) {
		Query query = entityManager.createNativeQuery(getSql(diaDaSemana, periodo));
		List<Object> listaHorarios = query.getResultList();
		List<Horario> horarios = new ArrayList<>();
		listaHorarios.stream().forEach(h -> {
			Object[] valor = (Object[])h;
			Horario horario = new Horario();
			if(valor[0] != null)
				horario.setIdAula(Integer.parseInt(valor[0].toString()));
			if(valor[1] != null)
				horario.setSala(Integer.parseInt(valor[1].toString()));
			if(valor[2] != null)
				horario.setInicio(LocalTime.parse(valor[2].toString()));
			if(valor[3] != null)
				horario.setFim(LocalTime.parse(valor[3].toString()));
			if(valor[4] != null)
				horario.setNomeDaDisciplina(valor[4].toString());
			if(valor[5] != null)
				horario.setProfessor(valor[5].toString());
			if(valor[6] != null)
				horario.setProfessorSubstituto(valor[6].toString());
			horarios.add(horario);
		});
		
		return horarios;
	}
	
	private String getSql(Integer diaDaSemana, Integer periodo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("select a.id as idAula, t.sala, a.inicio_aula inicio, a.fim_aula fim, d.nome_disciplina disciplina, prof.nome professor, subs.nome professorSubstituto ");
		sql.append("from aula a ");
		sql.append("inner join turma t on t.id = a.turma_id ");
		sql.append("inner join disciplina d on d.id = a.disciplina_id ");
		sql.append("left join usuario prof on prof.id = a.professor_id ");
		sql.append("left join usuario subs on subs.id = a.professor_substituto_id ");
		sql.append("where a.dia_da_semana = "+EnumSemana.toEnum(diaDaSemana).getCodigo()+" ");
		sql.append("and a.periodo = "+EnumPeriodo.toEnum(periodo).getCodigo()+" ");
		sql.append("order by a.inicio_aula asc, t.sala asc;");
		return sql.toString();
	}

	

}