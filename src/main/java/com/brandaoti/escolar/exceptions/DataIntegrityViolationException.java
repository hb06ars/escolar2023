package com.brandaoti.escolar.exceptions;

//Usada para tratamento de erros.
public class DataIntegrityViolationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataIntegrityViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataIntegrityViolationException(String message) {
		super(message);
	}

}
