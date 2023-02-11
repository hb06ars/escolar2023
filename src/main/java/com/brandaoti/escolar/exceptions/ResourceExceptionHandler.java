package com.brandaoti.escolar.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


// É o manipulador de exceções

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<StandardError> objectnotFoundException(AccessDeniedException ex,
			HttpServletRequest request) {

		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Seu perfil não pode acessar essa página.", ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectnotFoundException(ObjectNotFoundException ex,
			HttpServletRequest request) {

		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Objeto não encontrado", ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex,
			HttpServletRequest request) {

		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Violação de dados", ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationErrors(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		ValidationError errors = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), 
				"Violação de dados", "Erro na validação dos campos.", request.getRequestURI());
		
		for(FieldError x : ex.getBindingResult().getFieldErrors()) {
			errors.addError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}








