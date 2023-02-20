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
import com.brandaoti.escolar.dtos.AulaDTO;
import com.brandaoti.escolar.services.AulaService;

@RestController
@RequestMapping(value = "/aulas")
public class AulaResource {

	@Autowired
	private AulaService aulaService;
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR', 'ALUNO', 'VISITANTE')")
	public ResponseEntity<AulaDTO> findById(@PathVariable Integer id) {
		Aula obj = aulaService.findById(id);
		return ResponseEntity.ok().body(new AulaDTO(obj));
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR', 'ALUNO', 'VISITANTE')")
	public ResponseEntity<List<AulaDTO>> findAll() {
		List<Aula> list = aulaService.findAll();
		List<AulaDTO> listDTO = list.stream().map(obj -> new AulaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@PostMapping
	public ResponseEntity<AulaDTO> create(@Valid @RequestBody AulaDTO objDTO) {
		Aula newObj = aulaService.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<AulaDTO> update(@PathVariable Integer id, @Valid @RequestBody AulaDTO objDTO) {
		Aula obj = aulaService.update(id, objDTO);
		return ResponseEntity.ok().body(new AulaDTO(obj));
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<AulaDTO> delete(@PathVariable Integer id) {
		aulaService.delete(id); 
		return ResponseEntity.noContent().build();
	}

}
 

















