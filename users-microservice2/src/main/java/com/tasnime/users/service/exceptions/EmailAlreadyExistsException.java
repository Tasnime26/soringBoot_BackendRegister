package com.tasnime.users.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)//je dit a spring boot lorsque cette exception est levée le status qui soit retourner cest pas 200 qui est ok cest le status bad request qui est 400
public class EmailAlreadyExistsException extends RuntimeException {
	private String message;
	public EmailAlreadyExistsException (String message) {
		super(message);
	}

}
