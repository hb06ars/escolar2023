package com.brandaoti.escolar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.brandaoti.escolar.services.DBService;

@Configuration // Classe de configuração
@Profile("dev") //test que vem do application-test.properties depois do traço
public class DevConfig {
	
	@Autowired
	private DBService dBService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String value;
	
	//Toda vez que o test do aplication properties estiver ativo vai chamar de forma automatica (BEAN) esse metodo de instanciaDB
	@Bean
	public boolean instanciaDB() {
		if(value.equals("create")) { //Quero criar as tabelas
			this.dBService.instanciaDB(); //Método que irá criar as tabelas
		}
		return false;
	}
	
	
}

