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
import com.brandaoti.escolar.repositories.AlunoRepository;
import com.brandaoti.escolar.repositories.UsuarioRepository;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository Repository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Usuario buscarEmailAluno(String email) throws ObjectNotFoundException {
		Optional<Usuario> obj = Repository.buscarEmailAluno(email); 
		return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
	}
	
	public Usuario buscarCpfAluno(String cpf) throws ObjectNotFoundException {
		Optional<Usuario> obj = Repository.buscarCpfAluno(cpf); 
		return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
	}

	public Usuario buscarIdAluno(Integer id) {
		Optional<Usuario> obj = Repository.buscarIdAluno(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public Optional<List<Usuario>> buscarTodosAlunos() {
		return Repository.buscarTodosAlunos();
	}
	
	public Optional<List<Usuario>> listarTurmaDeAlunos(Integer id) {
		return Repository.listarTurmaDeAlunos(id);
	}

	public Usuario create(UsuarioDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Usuario newObj = new Usuario(objDTO);
		return Repository.save(newObj);
	}
 
	public Usuario update(Integer id, @Valid UsuarioDTO objDTO) {
		objDTO.setId(id);
		Usuario oldObj = buscarIdAluno(id);
		
		if(!objDTO.getSenha().equals(oldObj.getSenha())) 
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		
		validaPorCpfEEmail(objDTO);
		oldObj = new Usuario(objDTO);
		return Repository.save(oldObj);
	}

	public void delete(Integer id) {
		Repository.deleteById(id);
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
