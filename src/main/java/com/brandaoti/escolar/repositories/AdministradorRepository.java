package com.brandaoti.escolar.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.brandaoti.escolar.domain.Usuario;

public interface AdministradorRepository extends JpaRepository<Usuario, Integer>{

	@Query("SELECT u FROM Usuario u WHERE u.email = :email and upper(u.perfil.descricao) like 'ADMINISTRADOR' ")
	Optional<Usuario> buscarEmailAdministrador(String email);
	
	@Query("SELECT u FROM Usuario u WHERE u.cpf = :cpf and upper(u.perfil.descricao) like 'ADMINISTRADOR' ")
	Optional<Usuario> buscarCpfAdministrador(String cpf);
	
	@Query("SELECT u FROM Usuario u WHERE u.id = :id and upper(u.perfil.descricao) like 'ADMINISTRADOR' ")
	Optional<Usuario> buscarIdAdministrador(Integer id);
	
	@Query("SELECT u FROM Usuario u WHERE upper(u.perfil.descricao) like 'ADMINISTRADOR' ")
	Optional<List<Usuario>> buscarTodosAdministradores();
	
}