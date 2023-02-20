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

import com.brandaoti.escolar.domain.Disciplina;
import com.brandaoti.escolar.dtos.DisciplinaDTO;
import com.brandaoti.escolar.services.DisciplinaService;

@RestController
@RequestMapping(value = "/disciplinas")
public class DisciplinaResource {

	@Autowired
	private DisciplinaService disciplinaService;
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR', 'ALUNO')")
	public ResponseEntity<DisciplinaDTO> findById(@PathVariable Integer id) {
		Disciplina obj = disciplinaService.findById(id);
		return ResponseEntity.ok().body(new DisciplinaDTO(obj));
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR', 'ALUNO')")
	public ResponseEntity<List<DisciplinaDTO>> findAll() {
		List<Disciplina> list = disciplinaService.findAll();
		List<DisciplinaDTO> listDTO = list.stream().map(obj -> new DisciplinaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@PostMapping
	public ResponseEntity<DisciplinaDTO> create(@Valid @RequestBody DisciplinaDTO objDTO) {
		Disciplina newObj = disciplinaService.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<DisciplinaDTO> update(@PathVariable Integer id, @Valid @RequestBody DisciplinaDTO objDTO) {
		Disciplina obj = disciplinaService.update(id, objDTO);
		return ResponseEntity.ok().body(new DisciplinaDTO(obj));
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<DisciplinaDTO> delete(@PathVariable Integer id) {
		disciplinaService.delete(id); 
		return ResponseEntity.noContent().build();
	}

}
 

















