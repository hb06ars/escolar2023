package com.brandaoti.escolar.resources;

import java.net.URI;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.brandaoti.escolar.domain.Disciplina;
import com.brandaoti.escolar.domain.Turma;
import com.brandaoti.escolar.domain.Usuario;
import com.brandaoti.escolar.domain.enums.EnumSemana;
import com.brandaoti.escolar.dtos.AulaDTO;
import com.brandaoti.escolar.dtos.TurmaIgnoraSenhaDTO;
import com.brandaoti.escolar.services.AulaService;
import com.brandaoti.escolar.services.DisciplinaService;
import com.brandaoti.escolar.services.ProfessorService;
import com.brandaoti.escolar.services.TurmaService;
import com.brandaoti.escolar.services.UsuarioService;

@RestController
@RequestMapping(value = "/aulas")
public class AulaResource {
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AulaService aulaService;
	
	@Autowired
	private TurmaService turmaService;
	
	@Autowired
	private DisciplinaService disciplinaService;
	
	@Autowired
	private ProfessorService professorService;
	
	// AULAS DA SEMANA
	@GetMapping(value = "/minhasaulas")
	@PreAuthorize("hasAnyRole('PROFESSOR')")
	public ResponseEntity<List<AulaDTO>> minhasaulas() {
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Usuario obj = usuarioService.findByEmail(email);
		EnumSemana diaSemana = EnumSemana.toEnum(buscarDiaSemana());
		List<Aula> list = aulaService.minhasaulas(obj.getId()).stream()
			.filter(l -> l.getDiaDaSemana().getDescricao().equals(diaSemana.getDescricao())).collect(Collectors.toList());
		List<AulaDTO> aulas = list.stream().map(item -> new AulaDTO(item)).collect(Collectors.toList());
		return ResponseEntity.ok().body(aulas);
	}
	
	// TODAS AS AULAS
	@GetMapping(value = "/todasminhasaulas")
	@PreAuthorize("hasAnyRole('PROFESSOR')")
	public ResponseEntity<List<AulaDTO>> todasminhasaulas() {
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Usuario obj = usuarioService.findByEmail(email);
		List<Aula> list = aulaService.minhasaulas(obj.getId());
		List<AulaDTO> aulas = list.stream().map(item -> new AulaDTO(item)).collect(Collectors.toList());
		return ResponseEntity.ok().body(aulas);
	}

	
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
		
		Disciplina disciplina = disciplinaService.findNomeDisciplina(objDTO.getDisciplina().getNomeDisciplina());
		Usuario professor = professorService.buscarIdProfessor(Integer.parseInt(objDTO.getProfessor().getNome().split(" - ")[0]));
		objDTO.setDisciplina(disciplina);
		objDTO.setProfessor(professor);
		// Atualizando turma
		Turma turmaAntiga = turmaService.findBySerieTurma(objDTO.getTurma().getSerie(), objDTO.getTurma().getTurma());
		TurmaIgnoraSenhaDTO turmaAtualizada = new TurmaIgnoraSenhaDTO(turmaAntiga);
		turmaAtualizada.setSerie(objDTO.getTurma().getSerie());
		turmaAtualizada.setTurma(objDTO.getTurma().getTurma());
		turmaAtualizada.setSala(objDTO.getTurma().getSala());
		turmaService.updateIgnorandoSenha(turmaAtualizada.getId(), turmaAtualizada);
		turmaAntiga = turmaService.findBySerieTurma(objDTO.getTurma().getSerie(), objDTO.getTurma().getTurma());
		objDTO.setTurma(turmaAntiga);
		objDTO.setProfessorSubstituto(null);
		Aula newObj = aulaService.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<AulaDTO> update(@PathVariable Integer id, @Valid @RequestBody AulaDTO objDTO) {
		Disciplina disciplina = disciplinaService.findNomeDisciplina(objDTO.getDisciplina().getNomeDisciplina());
		if(objDTO.getProfessor() != null && objDTO.getProfessor().getNome() != null && !objDTO.getProfessor().getNome().equals("")) {
			Usuario professor = professorService.buscarIdProfessor(Integer.parseInt(objDTO.getProfessor().getNome().split(" - ")[0]));
			objDTO.setProfessor(professor);
		} else {
			objDTO.setProfessor(null);
		}
		if(objDTO.getProfessorSubstituto() != null && objDTO.getProfessorSubstituto().getNome() != null && !objDTO.getProfessorSubstituto().getNome().equals("")) {
			Usuario professorSubstituto = professorService.buscarIdProfessor(Integer.parseInt(objDTO.getProfessorSubstituto().getNome().split(" - ")[0]));
			objDTO.setProfessorSubstituto(professorSubstituto);	
		} else {
			objDTO.setProfessorSubstituto(null);
		}
		objDTO.setDisciplina(disciplina);
		Aula obj = aulaService.update(id, objDTO);
		// Atualizando turma
		Turma turmaAntiga = turmaService.findById(objDTO.getTurma().getId());
		TurmaIgnoraSenhaDTO turmaAtualizada = new TurmaIgnoraSenhaDTO(turmaAntiga);
		turmaAtualizada.setSerie(objDTO.getTurma().getSerie());
		turmaAtualizada.setTurma(objDTO.getTurma().getTurma());
		turmaAtualizada.setSala(objDTO.getTurma().getSala());
		turmaService.updateIgnorandoSenha(turmaAtualizada.getId(), turmaAtualizada);
		
		return ResponseEntity.ok().body(new AulaDTO(obj));
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<AulaDTO> delete(@PathVariable Integer id) {
		aulaService.delete(id); 
		return ResponseEntity.noContent().build();
	}
	
	
	private Integer buscarDiaSemana() {
		LocalDate hoje = LocalDate.now();
	    DayOfWeek day = hoje.getDayOfWeek();
	    switch (day.getValue()) {
            case 1: // SEGUNDA
            	return EnumSemana.SEGUNDA.getCodigo();
            case 2: // TERCA
            	return EnumSemana.TERCA.getCodigo();
            case 3: // QUARTA
            	return EnumSemana.QUARTA.getCodigo();
            case 4: // QUINTA
            	return EnumSemana.QUINTA.getCodigo();
            case 5: // SEXTA
            	return EnumSemana.SEXTA.getCodigo();
            case 6: // SABADO
            	return EnumSemana.SABADO.getCodigo();
            case 7: // DOMINGO
            	return EnumSemana.DOMINGO.getCodigo();
            default:
            	return null;
        }
	}


	

}
 

















