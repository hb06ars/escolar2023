package com.brandaoti.escolar.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.brandaoti.escolar.domain.Usuario;

public interface ProfessorRepository extends JpaRepository<Usuario, Integer>{

	@Query("SELECT u FROM Usuario u WHERE u.email = :email and upper(u.perfil.descricao) like 'PROFESSOR' ")
	Optional<Usuario> buscarEmailProfessor(String email);
	
	@Query("SELECT u FROM Usuario u WHERE u.cpf = :cpf and upper(u.perfil.descricao) like 'PROFESSOR' ")
	Optional<Usuario> buscarCpfProfessor(String cpf);
	
	@Query("SELECT u FROM Usuario u WHERE u.id = :id and upper(u.perfil.descricao) like 'PROFESSOR' ")
	Optional<Usuario> buscarIdProfessor(Integer id);
	
	@Query("SELECT u FROM Usuario u WHERE upper(u.perfil.descricao) like 'PROFESSOR' ")
	Optional<List<Usuario>> buscarTodosProfessores();
	
}