package com.brandaoti.escolar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brandaoti.escolar.domain.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer>{
	
	Optional<Perfil> findByCodigo(@Param("codigo") Integer codigo);
	
	@Query("SELECT p FROM Perfil p WHERE upper(p.descricao) like 'ADMINISTRADOR' ")
	Optional<Perfil> findPerfilAdministrador();
	
	@Query("SELECT p FROM Perfil p WHERE upper(p.descricao) like 'PROFESSOR' ")
	Optional<Perfil> findPerfilProfessor();
	
	@Query("SELECT p FROM Perfil p WHERE upper(p.descricao) like 'ALUNO' ")
	Optional<Perfil> findPerfilAluno();
	
	@Query("SELECT p FROM Perfil p WHERE upper(p.descricao) like 'VISITANTE' ")
	Optional<Perfil> findPerfilVisitante();
	
	
}