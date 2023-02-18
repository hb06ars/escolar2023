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
import com.brandaoti.escolar.repositories.AdministradorRepository;
import com.brandaoti.escolar.repositories.UsuarioRepository;

@Service
public class AdministradorService {

	@Autowired
	private AdministradorRepository administradorRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Usuario buscarEmailAdministrador(String email) throws ObjectNotFoundException {
		Optional<Usuario> obj = administradorRepository.buscarEmailAdministrador(email); 
		return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
	}
	
	public Usuario buscarCpfAdministrador(String cpf) throws ObjectNotFoundException {
		Optional<Usuario> obj = administradorRepository.buscarCpfAdministrador(cpf); 
		return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
	}

	public Usuario buscarIdAdministrador(Integer id) {
		Optional<Usuario> obj = administradorRepository.buscarIdAdministrador(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public Optional<List<Usuario>> buscarTodosAdministradores() {
		return administradorRepository.buscarTodosAdministradores();
	}

	public Usuario create(UsuarioDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Usuario newObj = new Usuario(objDTO);
		return administradorRepository.save(newObj);
	}
 
	public Usuario update(Integer id, @Valid UsuarioDTO objDTO) {
		objDTO.setId(id);
		Usuario oldObj = buscarIdAdministrador(id);
		
		if(!objDTO.getSenha().equals(oldObj.getSenha())) 
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		
		validaPorCpfEEmail(objDTO);
		oldObj = new Usuario(objDTO);
		return administradorRepository.save(oldObj);
	}

	public void delete(Integer id) {
		administradorRepository.deleteById(id);
	}

	private void validaPorCpfEEmail(UsuarioDTO objDTO) {
		Optional<Usuario> obj = usuarioRepository.findByCpf(objDTO.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}

		obj = usuarioRepository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}

}
