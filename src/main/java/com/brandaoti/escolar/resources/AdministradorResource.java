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

import com.brandaoti.escolar.domain.Administrador;
import com.brandaoti.escolar.dtos.AdministradorDTO;
import com.brandaoti.escolar.services.AdministradorService;

@RestController
@RequestMapping(value = "/administradores")
public class AdministradorResource {

	@Autowired
	private AdministradorService service;
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<AdministradorDTO> findById(@PathVariable Integer id) {
		Administrador obj = service.findById(id);
		return ResponseEntity.ok().body(new AdministradorDTO(obj));
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<List<AdministradorDTO>> findAll() {
		List<Administrador> list = service.findAll();
		List<AdministradorDTO> listDTO = list.stream().map(obj -> new AdministradorDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@PostMapping
	public ResponseEntity<AdministradorDTO> create(@Valid @RequestBody AdministradorDTO objDTO) {
		Administrador newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<AdministradorDTO> update(@PathVariable Integer id, @Valid @RequestBody AdministradorDTO objDTO) {
		Administrador obj = service.update(id, objDTO);
		return ResponseEntity.ok().body(new AdministradorDTO(obj));
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<AdministradorDTO> delete(@PathVariable Integer id) {
		service.delete(id); 
		return ResponseEntity.noContent().build();
	}

}
 

















