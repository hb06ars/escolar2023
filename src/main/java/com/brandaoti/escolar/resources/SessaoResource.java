package com.brandaoti.escolar.resources;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brandaoti.escolar.domain.Administrador;
import com.brandaoti.escolar.domain.Aluno;
import com.brandaoti.escolar.domain.Professor;
import com.brandaoti.escolar.domain.enums.Perfil;
import com.brandaoti.escolar.dtos.AdministradorDTO;
import com.brandaoti.escolar.dtos.AlunoDTO;
import com.brandaoti.escolar.dtos.PessoaDTO;
import com.brandaoti.escolar.dtos.ProfessorDTO;
import com.brandaoti.escolar.services.AdministradorService;
import com.brandaoti.escolar.services.AlunoService;
import com.brandaoti.escolar.services.ProfessorService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/meuacesso")
public class SessaoResource {

	@Autowired
	private AdministradorService serviceAdm;

	@Autowired
	private ProfessorService serviceProf;

	@Autowired
	private AlunoService serviceAluno;

	@GetMapping
	@PreAuthorize("hasAnyRole('ALUNO')")
	public ResponseEntity<?> meuUsuario() throws ObjectNotFoundException {
		return buscarUsuarioSessao();
	}
	
	@PutMapping
	@PreAuthorize("hasAnyRole('ALUNO')")
	public ResponseEntity<?> update(@Valid @RequestBody PessoaDTO objDTO) throws ObjectNotFoundException {
		return salvarUsuarioSessao(objDTO);
	}
	
	public ResponseEntity<?> buscarUsuarioSessao() throws ObjectNotFoundException {
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		return retornarInformacoes(email, perfis);
	}
	
	public ResponseEntity<?> salvarUsuarioSessao(PessoaDTO objDTO) throws ObjectNotFoundException {
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		return salvarInformacoes(email, perfis, objDTO);
	}

	private ResponseEntity<?> retornarInformacoes(String email, Collection<? extends GrantedAuthority> perfis) {
		if(perfis.stream().filter(a -> a.getAuthority().equals(Perfil.ADMINISTRADOR.getDescricao())).count() > 0) {
			return administrador(email);
		}
		if(perfis.stream().filter(a -> a.getAuthority().equals(Perfil.PROFESSOR.getDescricao())).count() > 0) {
			return professor(email);
		}
		if(perfis.stream().filter(a -> a.getAuthority().equals(Perfil.ALUNO.getDescricao())).count() > 0) {
			return aluno(email);
		}
		return null;
	}
	
	
	private ResponseEntity<?> salvarInformacoes(String email, Collection<? extends GrantedAuthority> perfis, PessoaDTO objAtualizado) {
		if(perfis.stream().filter(a -> a.getAuthority().equals(Perfil.ADMINISTRADOR.getDescricao())).count() > 0) {
			AdministradorDTO admnistrador = (AdministradorDTO) administrador(email).getBody();
			admnistrador.setCpf(objAtualizado.getCpf());
			admnistrador.setEmail(objAtualizado.getEmail());
			admnistrador.setNome(objAtualizado.getNome());
			admnistrador.setTelefone(objAtualizado.getEmail());
			serviceAdm.update(admnistrador.getId(), admnistrador);
			return administrador(email);
		}
		if(perfis.stream().filter(a -> a.getAuthority().equals(Perfil.PROFESSOR.getDescricao())).count() > 0) {
			return professor(email);
		}
		if(perfis.stream().filter(a -> a.getAuthority().equals(Perfil.ALUNO.getDescricao())).count() > 0) {
			return aluno(email);
		}
		return null;
	}
	
	
	
	private ResponseEntity<?> administrador(String email) {
		Administrador usuario = serviceAdm.findByEmail(email);
		return ResponseEntity.ok().body(new AdministradorDTO(usuario));
	}
	
	private ResponseEntity<?> professor(String email) {
		Professor usuario = serviceProf.findByEmail(email);
		return ResponseEntity.ok().body(new ProfessorDTO(usuario));
	}
	
	private ResponseEntity<?> aluno(String email) {
		Aluno usuario = serviceAluno.findByEmail(email);
		return ResponseEntity.ok().body(new AlunoDTO(usuario));
	}

}
 

















