package com.brandaoti.escolar.resources;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.brandaoti.escolar.domain.Aula;
import com.brandaoti.escolar.domain.Tabela;
import com.brandaoti.escolar.domain.Turma;
import com.brandaoti.escolar.domain.Usuario;
import com.brandaoti.escolar.domain.enums.EnumPeriodo;
import com.brandaoti.escolar.domain.enums.EnumSemana;
import com.brandaoti.escolar.dtos.TurmaDTO;
import com.brandaoti.escolar.dtos.TurmaIgnoraSenhaDTO;
import com.brandaoti.escolar.dtos.UsuarioDTO;
import com.brandaoti.escolar.services.AlunoService;
import com.brandaoti.escolar.services.AulaService;
import com.brandaoti.escolar.services.DisciplinaService;
import com.brandaoti.escolar.services.PerfilService;
import com.brandaoti.escolar.services.ProfessorService;
import com.brandaoti.escolar.services.TurmaService;
import com.brandaoti.escolar.utils.ProcessaExcel;

@RestController
@RequestMapping(value = "/")
public class UploadResource {

	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private AulaService aulaService;
	
	@Autowired
	private DisciplinaService disciplinaService;
	
	@Autowired
	private TurmaService turmaService;
	
	@Autowired
	private ProfessorService professorService;

	@PostMapping(value = "/uploadalunos" )
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR', 'ALUNO')")
	public ResponseEntity<String> uploadAlunos(@RequestParam("file")MultipartFile file) throws Exception {
		ProcessaExcel processaExcel = new ProcessaExcel();
		List<Tabela> tabelas = processaExcel.processar(file);
		Usuario aluno = new Usuario();
    	for(Tabela t : tabelas) {
    		switch (t.getColuna()) {
    			case 0 :
    				aluno = new Usuario();
    				aluno.setNome(t.getConteudo());
    				break;
    			case 1 :
    				aluno.setCpf(t.getConteudo());
    				break;
    			case 2 :
    				aluno.setTelefone(t.getConteudo());
    				break;
    			case 3 :
    				aluno.setEmail(t.getConteudo());
    				aluno.setSenha(aluno.getCpf());
    				aluno.setPerfil(perfilService.findPerfilAluno());
    				alunoService.create(new UsuarioDTO(aluno));
    				break;
    			default:
    				break;
    		}
    	}
    	
		return ResponseEntity.ok().body("Upload efetuado com sucesso.");
	}
	
	
	@PostMapping(value = "/uploadaulas")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR', 'ALUNO')")
	public ResponseEntity<String> uploadAulas(@RequestParam("file") MultipartFile file) throws Exception {
		ProcessaExcel processaExcel = new ProcessaExcel();
		List<Tabela> tabelas = processaExcel.processar(file);
		Aula aula = new Aula();
    	for(Tabela t : tabelas) {
    		switch (t.getColuna()) {
    			case 0 :
    				aula = new Aula();
    				aula.setDiaDaSemana(EnumSemana.valueOf(t.getConteudo().toUpperCase()));
    				break;
    			case 1 :
    				aula.setPeriodo(EnumPeriodo.valueOf(t.getConteudo().toUpperCase()));
    				break;
    			case 2 :
    				aula.setInicioAula(LocalTime.parse(t.getConteudo()));
    				break;
    			case 3 :
    				aula.setFimAula(LocalTime.parse(t.getConteudo()));
    				break;
    			case 4 :
    				aula.setDisciplina(disciplinaService.findNomeDisciplina(t.getConteudo().toUpperCase()));
    				break;
    			case 5 :
    				aula.setTurma(turmaService.findBySerieTurma(
    					Integer.parseInt(t.getConteudo().toUpperCase().split("ª")[0]), 
    					t.getConteudo().toUpperCase().split("ª")[1].toUpperCase())
    				);
    				break;
    			case 6 :
    				aula.setProfessor(professorService.buscarIdProfessor(Integer.parseInt(t.getConteudo().split(" - ")[0])));
    				salvarAula(aula);
    				break;
    			default:
    				break;
    		}
    	}
    	
		return ResponseEntity.ok().body("Upload efetuado com sucesso.");
	}

	
	@PostMapping(value = "/uploadturmas")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROFESSOR', 'ALUNO')")
	public ResponseEntity<String> uploadTurmas(@RequestParam("file") MultipartFile file) throws Exception {
		ProcessaExcel processaExcel = new ProcessaExcel();
		List<Tabela> tabelas = processaExcel.processar(file);
		Turma turma = new Turma();
    	for(Tabela t : tabelas) {
    		switch (t.getColuna()) {
    			case 0 :
    				turma = new Turma();
    				turma.setSerie(Integer.parseInt(t.getConteudo().toUpperCase()));
    				break;
    			case 1 :
    				turma.setTurma(t.getConteudo().toUpperCase());
    				break;
    			case 2 :
    				turma.setSala(Integer.parseInt(t.getConteudo().toUpperCase()));
    				break;
    			case 3 :
    				turma.setPeriodo(EnumPeriodo.valueOf(t.getConteudo().toUpperCase()));
    				turmaService.create(new TurmaDTO(turma));
    				break;
    			default:
    				break;
    		}
    	}
    	
		return ResponseEntity.ok().body("Upload efetuado com sucesso.");
	}
	
	

	private void salvarAula(Aula aula) {
		Turma turmaAntiga = turmaService.findBySerieTurma(aula.getTurma().getSerie(), aula.getTurma().getTurma());
		TurmaIgnoraSenhaDTO turmaAtualizada = new TurmaIgnoraSenhaDTO(turmaAntiga);
		turmaAtualizada.setSerie(aula.getTurma().getSerie());
		turmaAtualizada.setTurma(aula.getTurma().getTurma());
		turmaAtualizada.setSala(aula.getTurma().getSala());
		turmaAntiga = turmaService.findBySerieTurma(aula.getTurma().getSerie(), aula.getTurma().getTurma());
		aula.setTurma(turmaAntiga);
		aula.setProfessorSubstituto(null);
		aulaService.salvarComSenha(aula);
	}
	

}
