package com.brandaoti.escolar.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.brandaoti.escolar.domain.Aluno;
import com.brandaoti.escolar.domain.Pessoa;
import com.brandaoti.escolar.dtos.AlunoDTO;
import com.brandaoti.escolar.exceptions.DataIntegrityViolationException;
import com.brandaoti.escolar.exceptions.ObjectNotFoundException;
import com.brandaoti.escolar.repositories.AlunoRepository;
import com.brandaoti.escolar.repositories.PessoaRepository;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Aluno findById(Integer id) {
		Optional<Aluno> obj = repository.findById(id);  //Optional, pode ou nao encontrar o obj
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Aluno> findAll() {
		return repository.findAll();
	}

	public Aluno create(AlunoDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Aluno newObj = new Aluno(objDTO);
		return repository.save(newObj);
	}
 
	public Aluno update(Integer id, @Valid AlunoDTO objDTO) {
		objDTO.setId(id);
		Aluno oldObj = findById(id);
		
		if(!objDTO.getSenha().equals(oldObj.getSenha())) 
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		
		validaPorCpfEEmail(objDTO);
		oldObj = new Aluno(objDTO);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	private void validaPorCpfEEmail(AlunoDTO objDTO) {
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
