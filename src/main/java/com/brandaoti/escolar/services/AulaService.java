package com.brandaoti.escolar.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brandaoti.escolar.domain.Aula;
import com.brandaoti.escolar.dtos.AulaDTO;
import com.brandaoti.escolar.exceptions.ObjectNotFoundException;
import com.brandaoti.escolar.repositories.AulaRepository;

@Service
public class AulaService {

	@Autowired
	private AulaRepository aulaRepository;
	
	
	public Aula findById(Integer id) {
		Optional<Aula> obj = aulaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Aula> findAll() {
		return aulaRepository.findAll();
	}

	public Aula create(AulaDTO objDTO) {
		objDTO.setId(null);
		Aula newObj = new Aula(objDTO);
		return aulaRepository.save(newObj);
	}
 
	public Aula update(Integer id, @Valid AulaDTO objDTO) {
		objDTO.setId(id);
		Aula oldObj = findById(id);
		oldObj = new Aula(objDTO);
		return aulaRepository.save(oldObj);
	}

	public void delete(Integer id) {
		aulaRepository.deleteById(id);
	}


}
