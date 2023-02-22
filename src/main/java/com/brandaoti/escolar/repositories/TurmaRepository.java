package com.brandaoti.escolar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.brandaoti.escolar.domain.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Integer>{

	Optional<Turma> findBySerieAndTurma(Integer serie, String turma);

	@Query("SELECT t FROM Turma t inner join t.alunos a WHERE a.id IN (:idAluno)")
	Optional<Turma> findByTurmaDoAluno(Integer idAluno);

}