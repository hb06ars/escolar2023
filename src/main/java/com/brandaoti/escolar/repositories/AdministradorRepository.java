package com.brandaoti.escolar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brandaoti.escolar.domain.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer>{

	@Query(" select u from Administrador u where upper( u.email ) like upper( :email )")
	Optional<Administrador> findByEmail(@Param("email") String login_email);
	
}