package com.brandaoti.escolar.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.brandaoti.escolar.domain.Administrador;
import com.brandaoti.escolar.domain.Pessoa;
import com.brandaoti.escolar.dtos.AdministradorDTO;
import com.brandaoti.escolar.exceptions.DataIntegrityViolationException;
import com.brandaoti.escolar.exceptions.ObjectNotFoundException;
import com.brandaoti.escolar.repositories.AdministradorRepository;
import com.brandaoti.escolar.repositories.PessoaRepository;

@Service
public class AdministradorService {

	@Autowired
	private AdministradorRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Administrador findByEmail(String email) throws ObjectNotFoundException {
		Optional<Administrador> obj = repository.findByEmail(email); 
		return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
	}

	public Administrador findById(Integer id) {
		Optional<Administrador> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Administrador> findAll() {
		return repository.findAll();
	}

	public Administrador create(AdministradorDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Administrador newObj = new Administrador(objDTO);
		return repository.save(newObj);
	}
 
	public Administrador update(Integer id, @Valid AdministradorDTO objDTO) {
		objDTO.setId(id);
		Administrador oldObj = findById(id);
		
		if(!objDTO.getSenha().equals(oldObj.getSenha())) 
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		
		validaPorCpfEEmail(objDTO);
		oldObj = new Administrador(objDTO);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	private void validaPorCpfEEmail(AdministradorDTO objDTO) {
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
