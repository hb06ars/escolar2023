package com.brandaoti.escolar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandaoti.escolar.domain.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Integer>{

}