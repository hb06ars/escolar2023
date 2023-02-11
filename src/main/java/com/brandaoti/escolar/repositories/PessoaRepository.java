package com.brandaoti.escolar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brandaoti.escolar.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{
	Optional <Pessoa> findByCpf(String cpf);
	
	@Query(" select u from Pessoa u where upper( u.email ) like upper( :email )")
	Optional<Pessoa> findByEmail(@Param("email") String email);
	
}