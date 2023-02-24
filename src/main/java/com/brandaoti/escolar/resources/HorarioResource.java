package com.brandaoti.escolar.resources;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brandaoti.escolar.domain.Horario;
import com.brandaoti.escolar.domain.HorarioPrancheta;
import com.brandaoti.escolar.repositories.HorarioRepository;

@RestController
@RequestMapping(value = "/horario")
public class HorarioResource {
	
	@Autowired
	private HorarioRepository horarioRepository;

	@GetMapping(value = "/{diaDaSemana}/{periodo}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR', 'ALUNO', 'VISITANTE')")
	public ResponseEntity<List<HorarioPrancheta>> findById(@PathVariable Integer diaDaSemana, @PathVariable Integer periodo) {
		System.out.println(diaDaSemana +" - "+ periodo);
		List<Horario> horario = horarioRepository.buscarHorario(diaDaSemana, periodo);

		SortedSet<LocalTime> horariosNaoDuplicados = new TreeSet<>();
		horario.stream().forEach(h ->{
			horariosNaoDuplicados.add(h.getInicio());
		});
		
		List<HorarioPrancheta> listaPrancheta = new ArrayList<>();
		horariosNaoDuplicados.forEach(h ->{
			HorarioPrancheta item = new HorarioPrancheta();
			item.setHorario(h);
			item.setSalaProfessor(horario.stream().filter(ha -> ha.getInicio().equals(h)).collect(Collectors.toList()));
			listaPrancheta.add(item);
		});
		
		return ResponseEntity.ok().body(listaPrancheta);
	}
	
	
}
