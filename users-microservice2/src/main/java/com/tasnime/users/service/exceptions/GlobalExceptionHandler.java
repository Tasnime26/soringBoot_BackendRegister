package com.tasnime.users.service.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice //pour donner le bon code lorsque j'appel ca a partir de controller 
public class GlobalExceptionHandler {
//je doit definir un exception handler je dit lorsque un exception est levée retourne moi ce code bad request avec un message adequat
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
	 public ResponseEntity<ErrorDetails> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception,
	 WebRequest webRequest){
		//lorsque exception est levée je vais creer une instance de lobjet error details ou je vais donner time ily houa now 
	 ErrorDetails errorDetails = new ErrorDetails(
	 LocalDateTime.now(),
	 exception.getMessage(),//le message de lexception
	 webRequest.getDescription(false),
	 "USER_EMAIL_ALREADY_EXISTS"
	 );
	 return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	 }
	@ExceptionHandler(ExpiredTokenException.class)
	 public ResponseEntity<ErrorDetails> 
	handleExpiredTokenException(ExpiredTokenException exception,
	 WebRequest webRequest){
	 ErrorDetails errorDetails = new ErrorDetails(
	 LocalDateTime.now(),
	 exception.getMessage(),
	 webRequest.getDescription(false),
	 "EXPIRED_TOKEN"
	 );
	 return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	 }

	 @ExceptionHandler(InvalidTokenException.class)
	 public ResponseEntity<ErrorDetails> 
	handleInvalidTokenException(InvalidTokenException exception, 
	WebRequest webRequest){
	 ErrorDetails errorDetails = new ErrorDetails(
	 LocalDateTime.now(),
	 exception.getMessage(),
	 webRequest.getDescription(false),
	 "INVALID_TOKEN"
	 );
	 return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	 }
}
