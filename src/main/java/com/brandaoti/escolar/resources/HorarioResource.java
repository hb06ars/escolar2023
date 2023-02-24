package com.brandaoti.escolar.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brandaoti.escolar.domain.Horario;
import com.brandaoti.escolar.repositories.HorarioRepository;

@RestController
@RequestMapping(value = "/horario")
public class HorarioResource {
	
	@Autowired
	private HorarioRepository horarioRepository;

	@GetMapping(value = "/{diaDaSemana}/{periodo}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR', 'ALUNO', 'VISITANTE')")
	public ResponseEntity<List<Horario>> findById(@PathVariable Integer diaDaSemana, @PathVariable Integer periodo) {
		
		List<Horario> horario = horarioRepository.buscarHorario(diaDaSemana, periodo);
		System.out.println(horario.size());
		System.out.println(diaDaSemana + " - " + periodo);
		return ResponseEntity.ok().body(horario);
	}
	
	
}
