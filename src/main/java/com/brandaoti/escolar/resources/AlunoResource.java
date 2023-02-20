package com.brandaoti.escolar.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;
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

import com.brandaoti.escolar.domain.Usuario;
import com.brandaoti.escolar.dtos.UsuarioDTO;
import com.brandaoti.escolar.services.AlunoService;
import com.brandaoti.escolar.services.PerfilService;

@RestController
@RequestMapping(value = "/alunos")
public class AlunoResource {

	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private PerfilService perfilService;
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR')")
	public ResponseEntity<UsuarioDTO> findById(@PathVariable Integer id) {
		Usuario obj = alunoService.buscarIdAluno(id);
		obj.setSenha(null);
		return ResponseEntity.ok().body(new UsuarioDTO(obj));
	}

	@GetMapping(value = "/listaralunos")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR')")
	public ResponseEntity<List<UsuarioDTO>> findAllAlunos() {
		Optional<List<Usuario>> list = alunoService.buscarTodosAlunos();
		List<UsuarioDTO> listDTO = list.get().stream().map(obj -> new UsuarioDTO(obj)).collect(Collectors.toList());
		listDTO.forEach(l -> l.setSenha(null));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@PostMapping
	public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioDTO objDTO) {
		objDTO.setPerfil(perfilService.findPerfilAluno());
		Usuario newObj = alunoService.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> update(@PathVariable Integer id, @Valid @RequestBody UsuarioDTO objDTO) {
		Usuario objAntigo = alunoService.buscarIdAluno(objDTO.getId());
		objDTO.setSenha(objAntigo.getSenha());
		objDTO.setPerfil(perfilService.findPerfilAluno());
		Usuario obj = alunoService.update(id, objDTO);
		return ResponseEntity.ok().body(new UsuarioDTO(obj));
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> delete(@PathVariable Integer id) {
		alunoService.delete(id); 
		return ResponseEntity.noContent().build();
	}

}
 

















