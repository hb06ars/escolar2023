package com.brandaoti.escolar.resources;

import java.net.URI;
import java.time.LocalDate;
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

import com.brandaoti.escolar.domain.Turma;
import com.brandaoti.escolar.domain.Usuario;
import com.brandaoti.escolar.dtos.TurmaDTO;
import com.brandaoti.escolar.dtos.TurmaIgnoraSenhaDTO;
import com.brandaoti.escolar.dtos.UsuarioDTO;
import com.brandaoti.escolar.services.AlunoService;
import com.brandaoti.escolar.services.PerfilService;
import com.brandaoti.escolar.services.TurmaService;

@RestController
@RequestMapping(value = "/alunos")
public class AlunoResource {

	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private TurmaService turmaService;
	
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR')")
	public ResponseEntity<UsuarioDTO> findById(@PathVariable Integer id) {
		Usuario obj = alunoService.buscarIdAluno(id);
		obj.setSenha(null);
		return ResponseEntity.ok().body(new UsuarioDTO(obj));
	}
	
	@GetMapping(value = "/removerTurmaDoAluno/{idAluno}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<TurmaIgnoraSenhaDTO> removerTurmaDoAluno(@PathVariable Integer idAluno) {
		Turma t = turmaService.findByTurmaDoAluno(idAluno);
		List<Usuario> lista = t.getAlunos();
		for(int i = 0; i < lista.size(); i++) {
			if(idAluno.equals(lista.get(i).getId())) {
				lista.remove(i);
				break;
			}
		}
		t.setDataAtualizacao(LocalDate.now());
		t.setAlunos(lista);
		turmaService.updateIgnorandoSenha(t.getId(), new TurmaIgnoraSenhaDTO(t));
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping(value = "/adicionarAlunoNaTurma/{cpfAluno}/{idTurma}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	public ResponseEntity<TurmaIgnoraSenhaDTO> adicionarAlunoNaTurma(@PathVariable String cpfAluno, @PathVariable Integer idTurma) {
		Optional<Turma> turmaAtual = turmaService.findByTurmaDoAlunoPorCpf(cpfAluno);
		Usuario aluno = alunoService.buscarCpfAluno(cpfAluno);
		Turma turmaNova = turmaService.findById(idTurma);
		
		if(turmaAtual.isPresent()) {
			for(int i = 0; i < turmaAtual.get().getAlunos().size(); i++) {
				if(turmaAtual.get().getAlunos().get(i).getCpf().equals(cpfAluno)) {
					turmaAtual.get().getAlunos().remove(i);
					turmaService.update(turmaAtual.get().getId(), new TurmaDTO(turmaAtual.get()));
					break;
				}
			}
		}
		
		turmaNova.getAlunos().add(aluno);
		turmaService.update(turmaNova.getId(), new TurmaDTO(turmaNova));
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/listaralunos")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR')")
	public ResponseEntity<List<UsuarioDTO>> findAllAlunos() {
		Optional<List<Usuario>> list = alunoService.buscarTodosAlunos();
		List<UsuarioDTO> listDTO = list.get().stream().map(obj -> new UsuarioDTO(obj)).collect(Collectors.toList());
		listDTO.forEach(l -> l.setSenha(null));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value = "/listarTurmaDeAlunos/{turmaId}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR')")
	public ResponseEntity<List<UsuarioDTO>> listarTurmaDeAlunos(@PathVariable Integer turmaId) {
		Optional<List<Usuario>> list = alunoService.listarTurmaDeAlunos(turmaId);
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
 

















