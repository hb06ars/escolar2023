package com.brandaoti.escolar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brandaoti.escolar.domain.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer>{
	
	@Query(" select u from Aluno u where upper( u.email ) like upper( :email )")
	Optional<Aluno> findByEmail(@Param("email") String login_email);
	
}