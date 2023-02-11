package com.brandaoti.escolar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brandaoti.escolar.domain.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Integer>{
	
	@Query(" select u from Professor u where upper( u.email ) like upper( :email )")
	Optional<Professor> findByEmail(@Param("email") String login_email);
	
}