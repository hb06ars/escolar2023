package com.brandaoti.escolar.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.brandaoti.escolar.domain.Disciplina;
import com.brandaoti.escolar.domain.Perfil;
import com.brandaoti.escolar.domain.Usuario;
import com.brandaoti.escolar.domain.enums.EnumPerfil;
import com.brandaoti.escolar.repositories.DisciplinaRepository;
import com.brandaoti.escolar.repositories.PerfilRepository;
import com.brandaoti.escolar.repositories.UsuarioRepository;

@Service //Essa classe serve para injeção de dependencias, o spring cria, destroi, etc
public class DBService {

	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
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
		
		
		// PROFESSORES
		usuarioRepository.save(new Usuario(null, "Albert Eintein", "87550937010", "adaa@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilProfessor().get()));
		usuarioRepository.save(new Usuario(null, "Isaac Newton", "08977294045", "addsfaa@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilProfessor().get()));
		usuarioRepository.save(new Usuario(null, "Juca Substituto Silva", "15788263069", "dfaddsfaa@mail.com", encoder.encode("123"), "(11)98888-8888", perfilRepository.findPerfilProfessor().get()));

		
		// DISCIPLINA
		disciplinaRepository.save(new Disciplina(null, 1, "PORTUGUÊS"));
		disciplinaRepository.save(new Disciplina(null, 2, "MATEMÁTICA"));
		disciplinaRepository.save(new Disciplina(null, 3, "CIÊNCIAS"));
		disciplinaRepository.save(new Disciplina(null, 4, "HISTÓRIA"));
		disciplinaRepository.save(new Disciplina(null, 5, "GEOGRAFIA"));
		disciplinaRepository.save(new Disciplina(null, 6, "INGLÊS"));
		disciplinaRepository.save(new Disciplina(null, 7, "ARTES"));
		disciplinaRepository.save(new Disciplina(null, 8, "ED. FÍSICA"));
		disciplinaRepository.save(new Disciplina(null, 9, "QUÍMICA"));
		disciplinaRepository.save(new Disciplina(null, 10, "FÍSICA"));
		disciplinaRepository.save(new Disciplina(null, 11, "BIOLOGIA"));
		disciplinaRepository.save(new Disciplina(null, 12, "SOCIOLOGIA"));

	}
	
	
}
