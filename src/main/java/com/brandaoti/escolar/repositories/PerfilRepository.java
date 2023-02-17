package com.brandaoti.escolar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.brandaoti.escolar.domain.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer>{
	
	Optional<Perfil> findByCodigo(@Param("codigo") Integer codigo);
	
	
}