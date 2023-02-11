package com.brandaoti.escolar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandaoti.escolar.domain.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer>{

}