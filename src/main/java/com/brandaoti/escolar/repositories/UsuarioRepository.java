package com.brandaoti.escolar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandaoti.escolar.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	Optional<Usuario> findByEmail(String email);
	Optional<Usuario> findByCpf(String cpf);
	
}