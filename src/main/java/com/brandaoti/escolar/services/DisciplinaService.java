package com.brandaoti.escolar.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brandaoti.escolar.domain.Disciplina;
import com.brandaoti.escolar.dtos.DisciplinaDTO;
import com.brandaoti.escolar.exceptions.ObjectNotFoundException;
import com.brandaoti.escolar.repositories.DisciplinaRepository;

@Service
public class DisciplinaService {

	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	public Disciplina findNomeDisciplina(String nomeDisciplina) {
		return disciplinaRepository.findByNomeDisciplina(nomeDisciplina);
	}
	
	public Disciplina findById(Integer id) {
		Optional<Disciplina> obj = disciplinaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Disciplina> findAll() {
		return disciplinaRepository.findAll();
	}

	public Disciplina create(DisciplinaDTO objDTO) {
		objDTO.setId(null);
		Disciplina newObj = new Disciplina(objDTO);
		return disciplinaRepository.save(newObj);
	}
 
	public Disciplina update(Integer id, @Valid DisciplinaDTO objDTO) {
		objDTO.setId(id);
		Disciplina oldObj = findById(id);
		oldObj = new Disciplina(objDTO);
		return disciplinaRepository.save(oldObj);
	}

	public void delete(Integer id) {
		disciplinaRepository.deleteById(id);
	}


}
