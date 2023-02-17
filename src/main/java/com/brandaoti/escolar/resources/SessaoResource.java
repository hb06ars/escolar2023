package com.brandaoti.escolar.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brandaoti.escolar.domain.Perfil;
import com.brandaoti.escolar.domain.Usuario;
import com.brandaoti.escolar.domain.enums.EnumPerfil;
import com.brandaoti.escolar.dtos.UsuarioDTO;
import com.brandaoti.escolar.services.PerfilService;
import com.brandaoti.escolar.services.UsuarioService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/meuacesso")
public class SessaoResource {

	@Autowired
	private UsuarioService serviceUser;

	@Autowired
	private PerfilService servicePerfil;

	@GetMapping
	@PreAuthorize("hasAnyRole({'ROLE_ADMINISTRADOR', 'ROLE_PROFESSOR', 'ROLE_ALUNO', 'ROLE_VISITANTE'})")
	public ResponseEntity<?> meuUsuario() throws ObjectNotFoundException {
		return buscarUsuarioSessao();
	}
	
	@PutMapping
	@PreAuthorize("hasAnyRole({'ROLE_ADMINISTRADOR', 'ROLE_PROFESSOR', 'ROLE_ALUNO', 'ROLE_VISITANTE'})")
	public ResponseEntity<?> update(@Valid @RequestBody UsuarioDTO objDTO) throws ObjectNotFoundException {
		return salvarUsuarioSessao(objDTO);
	}
	
	public ResponseEntity<?> buscarUsuarioSessao() throws ObjectNotFoundException {
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		//Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		Usuario obj = serviceUser.findByEmail(email);
		return ResponseEntity.ok().body(new UsuarioDTO(obj));
	}
	
	public ResponseEntity<?> salvarUsuarioSessao(UsuarioDTO objDTO) throws ObjectNotFoundException {
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Collection<? extends GrantedAuthority> perfis = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		return salvarInformacoes(email, perfis, objDTO);
	}

	
	
	private ResponseEntity<?> salvarInformacoes(String email, Collection<? extends GrantedAuthority> perfis, UsuarioDTO objAtualizado) {
		if(perfis.stream().filter(a -> a.getAuthority().equals(EnumPerfil.ADMINISTRADOR.getRole())).count() > 0) {
			//Atualmente ele é um Administrador.
			Usuario usuario = serviceUser.findByEmail(email);
			UsuarioDTO admnistrador = new UsuarioDTO(usuario); 
			admnistrador.setCpf(objAtualizado.getCpf());
			admnistrador.setEmail(objAtualizado.getEmail());
			admnistrador.setNome(objAtualizado.getNome());
			admnistrador.setTelefone(objAtualizado.getTelefone());
			admnistrador.setPerfil(servicePerfil.findByCodigo(objAtualizado.getPerfil().getId()));
			serviceUser.update(admnistrador.getId(), admnistrador);
			atualizarSessao(objAtualizado.getEmail(), objAtualizado.getSenha(), admnistrador.getPerfil());
			return ResponseEntity.of(Optional.of(admnistrador));
		} else {
			Usuario usuario = serviceUser.findByEmail(email);
			UsuarioDTO NaoEAdministrador = new UsuarioDTO(usuario); 
			NaoEAdministrador.setCpf(objAtualizado.getCpf());
			NaoEAdministrador.setEmail(objAtualizado.getEmail());
			NaoEAdministrador.setNome(objAtualizado.getNome());
			NaoEAdministrador.setTelefone(objAtualizado.getTelefone());
			NaoEAdministrador.setPerfil(servicePerfil.findByCodigo(objAtualizado.getPerfil().getId()));
			serviceUser.update(NaoEAdministrador.getId(), NaoEAdministrador);
			atualizarSessao(objAtualizado.getEmail(), objAtualizado.getSenha(), NaoEAdministrador.getPerfil());
			return ResponseEntity.of(Optional.of(NaoEAdministrador));
		}
		
	}
	
	
	
	private void atualizarSessao(String email, String senha, Perfil perfil){
		List<GrantedAuthority> listaDePermissoes = new ArrayList<>();
		SimpleGrantedAuthority permissaoNova = new SimpleGrantedAuthority(perfil.getRole());
		listaDePermissoes.addAll(Arrays.asList(permissaoNova));
		Authentication newAuth = new UsernamePasswordAuthenticationToken(email, senha, listaDePermissoes);
		System.out.println("SALVO");
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}
}
 

















