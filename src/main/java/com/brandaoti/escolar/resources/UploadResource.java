package com.brandaoti.escolar.resources;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.brandaoti.escolar.services.AlunoService;
import com.brandaoti.escolar.services.AulaService;
import com.brandaoti.escolar.services.TurmaService;

@RestController
@RequestMapping(value = "/")
public class UploadResource {

	@Autowired
	private TurmaService turmaService;
	
	@Autowired
	private AulaService aulaService;

	@Autowired
	private AlunoService alunoService;

	@PostMapping(value = "/uploadalunos")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR', 'ALUNO')")
	public ResponseEntity<String> uploadAlunos(@ModelAttribute MultipartFile file) {

		return ResponseEntity.ok().body("Upload efetuado com sucesso.");
	}
	
	
	@PostMapping(value = "/uploadaulas")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR', 'ALUNO')")
	public ResponseEntity<String> uploadAulas(@ModelAttribute MultipartFile file) {

		return ResponseEntity.ok().body("Upload efetuado com sucesso.");
	}
	

}
