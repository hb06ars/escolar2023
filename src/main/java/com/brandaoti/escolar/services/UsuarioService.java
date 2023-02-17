package com.brandaoti.escolar.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.brandaoti.escolar.domain.Usuario;
import com.brandaoti.escolar.dtos.UsuarioDTO;
import com.brandaoti.escolar.exceptions.DataIntegrityViolationException;
import com.brandaoti.escolar.exceptions.ObjectNotFoundException;
import com.brandaoti.escolar.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Usuario findByEmail(String email) throws ObjectNotFoundException {
		Optional<Usuario> obj = repository.findByEmail(email); 
		return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
	}

	public Usuario findById(Integer id) {
		Optional<Usuario> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Usuario> findAll() {
		return repository.findAll();
	}

	public Usuario create(UsuarioDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Usuario newObj = new Usuario(objDTO);
		return repository.save(newObj);
	}
 
	public Usuario update(Integer id, @Valid UsuarioDTO objDTO) {
		objDTO.setId(id);
		Usuario oldObj = findById(id);
		
		if(!objDTO.getSenha().equals(oldObj.getSenha())) 
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		
		validaPorCpfEEmail(objDTO);
		oldObj = new Usuario(objDTO);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	private void validaPorCpfEEmail(UsuarioDTO objDTO) {
		Optional<Usuario> obj = repository.findByCpf(objDTO.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}

		obj = repository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}

}
