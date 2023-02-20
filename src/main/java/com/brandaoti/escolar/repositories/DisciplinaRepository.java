package com.brandaoti.escolar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandaoti.escolar.domain.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer>{

	Disciplina findByNomeDisciplina(String nomeDisciplina);

}