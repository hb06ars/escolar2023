package com.brandaoti.escolar.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.brandaoti.escolar.domain.Usuario;

public interface AlunoRepository extends JpaRepository<Usuario, Integer>{

	@Query("SELECT u FROM Usuario u WHERE u.email = :email and upper(u.perfil.descricao) like 'ALUNO' ")
	Optional<Usuario> buscarEmailAluno(String email);
	
	@Query("SELECT u FROM Usuario u WHERE u.cpf = :cpf and upper(u.perfil.descricao) like 'ALUNO' ")
	Optional<Usuario> buscarCpfAluno(String cpf);
	
	@Query("SELECT u FROM Usuario u WHERE u.id = :id and upper(u.perfil.descricao) like 'ALUNO' ")
	Optional<Usuario> buscarIdAluno(Integer id);
	
	@Query("SELECT u FROM Usuario u WHERE upper(u.perfil.descricao) like 'ALUNO' ")
	Optional<List<Usuario>> buscarTodosAlunos();
	
	@Query("SELECT t.alunos FROM Turma t WHERE t.id = :id")
	Optional<List<Usuario>> listarTurmaDeAlunos(Integer id);
	
	
}