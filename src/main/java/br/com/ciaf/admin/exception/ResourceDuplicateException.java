package br.com.ciaf.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceDuplicateException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ResourceDuplicateException(Object cprod) {
		super("Dado Duplicado = " + cprod);
	}

	public ResourceDuplicateException(String cprod) {
		super();
	}

}
