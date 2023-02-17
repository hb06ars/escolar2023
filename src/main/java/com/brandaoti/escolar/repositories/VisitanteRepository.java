package com.brandaoti.escolar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brandaoti.escolar.domain.Visitante;

public interface VisitanteRepository extends JpaRepository<Visitante, Integer>{
	
	@Query(" select u from Visitante u where upper( u.email ) like upper( :email )")
	Optional<Visitante> findByEmail(@Param("email") String login_email);
	
}