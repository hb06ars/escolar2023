package com.brandaoti.escolar.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brandaoti.escolar.domain.Turma;
import com.brandaoti.escolar.dtos.TurmaDTO;
import com.brandaoti.escolar.dtos.TurmaIgnoraSenhaDTO;
import com.brandaoti.escolar.exceptions.ObjectNotFoundException;
import com.brandaoti.escolar.repositories.TurmaRepository;

@Service
public class TurmaService {

	@Autowired
	private TurmaRepository turmaRepository;
	
	
	public Turma findById(Integer id) {
		Optional<Turma> obj = turmaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Turma> findAll() {
		return turmaRepository.findAll();
	}

	public Turma create(TurmaDTO objDTO) {
		objDTO.setId(null);
		Turma newObj = new Turma(objDTO);
		return turmaRepository.save(newObj);
	}
 
	public Turma update(Integer id, @Valid TurmaDTO objDTO) {
		objDTO.setId(id);
		Turma oldObj = findById(id);
		oldObj = new Turma(objDTO);
		return turmaRepository.save(oldObj);
	}

	public Turma updateIgnorandoSenha(Integer id, @Valid TurmaIgnoraSenhaDTO objDTO) {
		objDTO.setId(id);
		Turma oldObj = findById(id);
		oldObj = new Turma(objDTO);
		return turmaRepository.save(oldObj);
	}
	
	public void delete(Integer id) {
		turmaRepository.deleteById(id);
	}


}
