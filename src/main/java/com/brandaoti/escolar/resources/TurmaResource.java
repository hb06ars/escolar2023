package com.brandaoti.escolar.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.brandaoti.escolar.domain.Aula;
import com.brandaoti.escolar.domain.Turma;
import com.brandaoti.escolar.dtos.TurmaDTO;
import com.brandaoti.escolar.services.AulaService;
import com.brandaoti.escolar.services.TurmaService;

@RestController
@RequestMapping(value = "/turmas")
public class TurmaResource {

	@Autowired
	private TurmaService turmaService;
	
	@Autowired
	private AulaService aulaService;
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR', 'ALUNO')")
	public ResponseEntity<TurmaDTO> findById(@PathVariable Integer id) {
		Turma obj = turmaService.findById(id);
		return ResponseEntity.ok().body(new TurmaDTO(obj));
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR', 'ALUNO')")
	public ResponseEntity<List<TurmaDTO>> findAll() {
		List<Turma> list = turmaService.findAll();
		List<TurmaDTO> listDTO = list.stream().map(obj -> new TurmaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@PostMapping
	public ResponseEntity<TurmaDTO> create(@Valid @RequestBody TurmaDTO objDTO) {
		objDTO.setAlunos(null);
		Turma newObj = turmaService.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<TurmaDTO> update(@PathVariable Integer id, @Valid @RequestBody TurmaDTO objDTO) {
		Turma obj = turmaService.update(id, objDTO);
		return ResponseEntity.ok().body(new TurmaDTO(obj));
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TurmaDTO> delete(@PathVariable Integer id) {
		Turma turma = turmaService.findById(id);
		turma.setAlunos(null);
		turmaService.update(id, new TurmaDTO(turma));
		
		List<Aula> listaAulas = aulaService.buscarTurma(id);
		listaAulas.stream().forEach(l ->{
			l.setTurma(null);
		});
		aulaService.saveAll(listaAulas);
		
		turmaService.delete(id); 
		return ResponseEntity.noContent().build();
	}

}
 

















