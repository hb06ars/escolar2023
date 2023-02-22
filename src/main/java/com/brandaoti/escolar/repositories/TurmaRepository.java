package com.brandaoti.escolar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandaoti.escolar.domain.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Integer>{

	Optional<Turma> findBySerieAndTurma(Integer serie, String turma);

	
}