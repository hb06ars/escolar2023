package com.brandaoti.escolar.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.brandaoti.escolar.domain.Administrador;
import com.brandaoti.escolar.domain.Aluno;
import com.brandaoti.escolar.domain.Professor;
import com.brandaoti.escolar.repositories.PessoaRepository;

@Service //Essa classe serve para injeção de dependencias, o spring cria, destroi, etc
public class DBService {

	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public void instanciaDB() {

		Administrador adm = new Administrador(null, "Henrique Brandão", "550.482.150-95", "brandaoti@mail.com", encoder.encode("123"), "(11)98888-8888");
		
		Professor prof = new Professor(null, "Albert Eintein", "903.347.070-56", "albertEinstein@mail.com", encoder.encode("123"), "(11)98888-8887");
		
		Aluno aluno1 = new Aluno(null, "Maria Mercedes", "271.068.470-54", "maria@mail.com", encoder.encode("123"), "(11)98888-8886");
		
		Aluno aluno2 = new Aluno(null, "Juca de Sousa", "626.388.560-29", "juca@mail.com", encoder.encode("123"), "(11)98888-8885");
		
		pessoaRepository.saveAll(Arrays.asList(adm, prof, aluno1, aluno2));
	}
}
