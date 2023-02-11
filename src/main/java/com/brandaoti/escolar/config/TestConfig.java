package com.brandaoti.escolar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.brandaoti.escolar.services.DBService;

@Configuration // Classe de configuração
@Profile("test") //test que vem do application-test.properties depois do traço
public class TestConfig {

	@Autowired
	private DBService dbService;
	
	//Toda vez que o test do aplication properties estiver ativo vai chamar de forma automatica (BEAN) esse metodo de instanciaDB
	@Bean
	public void instanciaDB() {
		this.dbService.instanciaDB();
	}
}
