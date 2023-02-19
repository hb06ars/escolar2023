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
		
		Usuario adm = new Usuario(null, "Henrique Brandão", "550.482.150-95", "brandaoti@mail.com", encoder.encode("123"), "(11)98888-8888", perfil_adm);
		
		Usuario prof = new Usuario(null, "Albert Eintein", "903.347.070-56", "albertEinstein@mail.com", encoder.encode("123"), "(11)98888-8887", perfil_prof);
		
		Usuario aluno1 = new Usuario(null, "Maria Mercedes", "271.068.470-54", "maria@mail.com", encoder.encode("123"), "(11)98888-8886", perfil_aluno);
		
		Usuario aluno2 = new Usuario(null, "Juca de Sousa", "626.388.560-29", "juca@mail.com", encoder.encode("123"), "(11)98888-8885", perfil_visitante);
		
		usuarioRepository.saveAll(Arrays.asList(adm, prof, aluno1, aluno2));
		
		
		usuarioRepository.save(new Usuario(null, "AAA Brandão", "87550937010", "aaa@mail.com", encoder.encode("123"), "(11)98888-8888", perfil_adm));
		usuarioRepository.save(new Usuario(null, "SSS Brandão", "33187639003", "sss@mail.com", encoder.encode("123"), "(11)98888-8888", perfil_adm));
		usuarioRepository.save(new Usuario(null, "SSS Brandão", "21312763035", "ccc@mail.com", encoder.encode("123"), "(11)98888-8888", perfil_adm));
		usuarioRepository.save(new Usuario(null, "SSS Brandão", "94604866066", "cvv@mail.com", encoder.encode("123"), "(11)98888-8888", perfil_adm));
		usuarioRepository.save(new Usuario(null, "SSS Brandão", "63772024076", "ccv@mail.com", encoder.encode("123"), "(11)98888-8888", perfil_adm));
		usuarioRepository.save(new Usuario(null, "SSS Brandão", "62500637069", "ffg@mail.com", encoder.encode("123"), "(11)98888-8888", perfil_adm));
		
		
		
	}
}
