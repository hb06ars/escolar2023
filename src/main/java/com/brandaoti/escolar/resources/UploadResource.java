package com.brandaoti.escolar.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.brandaoti.escolar.domain.Tabela;
import com.brandaoti.escolar.services.AlunoService;
import com.brandaoti.escolar.services.AulaService;
import com.brandaoti.escolar.services.TurmaService;
import com.brandaoti.escolar.utils.ProcessaExcel;

@RestController
@RequestMapping(value = "/")
public class UploadResource {

	@Autowired
	private TurmaService turmaService;
	
	@Autowired
	private AulaService aulaService;

	@Autowired
	private AlunoService alunoService;

	@PostMapping(value = "/uploadalunos" )
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR', 'ALUNO')")
	public ResponseEntity<String> uploadAlunos(@RequestParam("file")MultipartFile file) throws Exception {
		ProcessaExcel processaExcel = new ProcessaExcel();
		List<Tabela> tabelas = processaExcel.uploadAlunos(file);
    	
		return ResponseEntity.ok().body("Upload efetuado com sucesso.");
	}
	
	
	@PostMapping(value = "/uploadaulas")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR', 'ALUNO')")
	public ResponseEntity<String> uploadAulas(@RequestParam("file") MultipartFile file) throws Exception {
		//ProcessaExcel processaExcel = new ProcessaExcel();
		//List<Tabela> tabelas = processaExcel.uploadAlunos(arquivo);
    	
		return ResponseEntity.ok().body("Upload efetuado com sucesso.");
	}
	

}
