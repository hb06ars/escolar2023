package com.brandaoti.escolar.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.brandaoti.escolar.domain.Aula;

public interface AulaRepository extends JpaRepository<Aula, Integer>{

	@Query("SELECT a FROM Aula a WHERE a.turma.id = :id")
	List<Aula> buscarTurma(Integer id);

	@Query("SELECT a FROM Aula a WHERE a.professor.id = :id order by a.diaDaSemana asc , a.inicioAula asc ")
	List<Aula> minhasaulas(Integer id);

	
}