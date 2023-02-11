package com.brandaoti.escolar.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.brandaoti.escolar.domain.Pessoa;
import com.brandaoti.escolar.domain.Professor;
import com.brandaoti.escolar.dtos.ProfessorDTO;
import com.brandaoti.escolar.exceptions.DataIntegrityViolationException;
import com.brandaoti.escolar.exceptions.ObjectNotFoundException;
import com.brandaoti.escolar.repositories.PessoaRepository;
import com.brandaoti.escolar.repositories.ProfessorRepository;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Professor findById(Integer id) {
		Optional<Professor> obj = repository.findById(id);  //Optional, pode ou nao encontrar o obj
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Professor> findAll() {
		return repository.findAll();
	}

	public Professor create(ProfessorDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Professor newObj = new Professor(objDTO);
		return repository.save(newObj);
	}
 
	public Professor update(Integer id, @Valid ProfessorDTO objDTO) {
		objDTO.setId(id);
		Professor oldObj = findById(id);
		
		if(!objDTO.getSenha().equals(oldObj.getSenha())) 
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		
		validaPorCpfEEmail(objDTO);
		oldObj = new Professor(objDTO);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	private void validaPorCpfEEmail(ProfessorDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}

		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}

}
