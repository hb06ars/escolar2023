package com.brandaoti.escolar.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.brandaoti.escolar.domain.Perfil;
import com.brandaoti.escolar.domain.Usuario;
import com.brandaoti.escolar.domain.enums.EnumPerfil;
import com.brandaoti.escolar.repositories.PerfilRepository;
import com.brandaoti.escolar.repositories.UsuarioRepository;

@Service //Essa classe serve para injeção de dependencias, o spring cria, destroi, etc
public class DBService {

	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public void instanciaDB() {
		
		Perfil perfil_adm = new Perfil(null, EnumPerfil.ADMINISTRADOR.getCodigo(), EnumPerfil.ADMINISTRADOR.getRole() , EnumPerfil.ADMINISTRADOR.getDescricao());
		Perfil perfil_prof = new Perfil(null, EnumPerfil.PROFESSOR.getCodigo(), EnumPerfil.PROFESSOR.getRole(), EnumPerfil.PROFESSOR.getDescricao());
		Perfil perfil_aluno = new Perfil(null, EnumPerfil.ALUNO.getCodigo(), EnumPerfil.ALUNO.getRole(), EnumPerfil.ALUNO.getDescricao());
		Perfil perfil_visitante = new Perfil(null, EnumPerfil.VISITANTE.getCodigo(), EnumPerfil.VISITANTE.getRole(), EnumPerfil.VISITANTE.getDescricao());
		perfilRepository.saveAll(Arrays.asList(perfil_adm, perfil_prof, perfil_aluno, perfil_visitante));
		
		preencherDados();
		
		
	}
	
	public void preencherDados() {
		// ADMINISTRADORES
		usuarioRepository.save(new Usuario(null, "Henrique Brandão", "06648949090", "brandaoti@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilAdministrador().get()));
		usuarioRepository.save(new Usuario(null, "José dos Santos", "33187639003", "sdss@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilAdministrador().get()));
		usuarioRepository.save(new Usuario(null, "Paulo da Silva", "21312763035", "ccc@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilAdministrador().get()));
		usuarioRepository.save(new Usuario(null, "Ana Maria de Paula", "94604866066", "cvv@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilAdministrador().get()));
		usuarioRepository.save(new Usuario(null, "Flávia Castro", "63772024076", "ccv@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilAdministrador().get()));
		usuarioRepository.save(new Usuario(null, "Márcia Ferreira", "62500637069", "ffg@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilAdministrador().get()));

		// PROFESSORES
		usuarioRepository.save(new Usuario(null, "Albert Eintein", "87550937010", "adaa@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilProfessor().get()));
		usuarioRepository.save(new Usuario(null, "Isaac Newton", "08977294045", "addsfaa@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilProfessor().get()));

		// ALUNOS
		usuarioRepository.save(new Usuario(null, "Maria Andrade", "48927543076", "adaadfgd@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilAluno().get()));
		usuarioRepository.save(new Usuario(null, "Maria Mercedes", "34975653001", "adaffga@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilAluno().get()));
		usuarioRepository.save(new Usuario(null, "Juca de Sousa", "26411925003", "ddsfadaa@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilAluno().get()));
		usuarioRepository.save(new Usuario(null, "Júlio dos Santos", "69773820076", "sdfsdddsfadaa@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilAluno().get()));
		usuarioRepository.save(new Usuario(null, "Flávio Oliveira", "92618616014", "ssdfsdfdfsdddsfadaa@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilAluno().get()));
		
		
		
		
		
	}
	
	
}
