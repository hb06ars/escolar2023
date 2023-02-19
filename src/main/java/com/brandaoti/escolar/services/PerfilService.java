package com.brandaoti.escolar.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brandaoti.escolar.domain.Perfil;
import com.brandaoti.escolar.dtos.PerfilDTO;
import com.brandaoti.escolar.exceptions.ObjectNotFoundException;
import com.brandaoti.escolar.repositories.PerfilRepository;

@Service
public class PerfilService {

	@Autowired
	private PerfilRepository repository;
	

	public Perfil findById(Integer id) {
		Optional<Perfil> obj = repository.findById(id);  //Optional, pode ou nao encontrar o obj
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}
	
	public Perfil findByCodigo(Integer codigo) {
		Optional<Perfil> obj = repository.findByCodigo(codigo);  //Optional, pode ou nao encontrar o obj
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Código: " + codigo));
	}
	
	public Perfil findPerfilAdministrador() {
		Optional<Perfil> obj = repository.findPerfilAdministrador();  //Optional, pode ou nao encontrar o obj
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
	}
	
	public Perfil findPerfilAluno() {
		Optional<Perfil> obj = repository.findPerfilAluno();  //Optional, pode ou nao encontrar o obj
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
	}
	
	public Perfil findPerfilVisitante() {
		Optional<Perfil> obj = repository.findPerfilVisitante();  //Optional, pode ou nao encontrar o obj
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
	}
	
	public Perfil findPerfilProfessor() {
		Optional<Perfil> obj = repository.findPerfilProfessor();  //Optional, pode ou nao encontrar o obj
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
	}

	public List<Perfil> findAll() {
		return repository.findAll();
	}

	public Perfil create(PerfilDTO objDTO) {
		objDTO.setId(null);
		objDTO.setCodigo(objDTO.getCodigo());
		objDTO.setRole(objDTO.getRole());
		objDTO.setDescricao(objDTO.getDescricao());
		Perfil newObj = new Perfil(objDTO);
		return repository.save(newObj);
	}
 
	public Perfil update(Integer id, @Valid PerfilDTO objDTO) {
		objDTO.setId(id);
		Perfil oldObj = findById(id);
		oldObj = new Perfil(objDTO);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}


}
