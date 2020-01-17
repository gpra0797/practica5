package com.everis.escuela.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.everis.escuela.exceptions.ErrorDetails;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;

@ControllerAdvice //interferir entre el controlador y la web/vista , controla errores http diferente a 200
public class GlobalHandlerException {
		
	@ExceptionHandler(ResourceNotFoundException.class)//Ejecutará este metodo
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request){//Response Entity , es response con http status
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ValidationException.class)//Ejecutará este metodo
	public ResponseEntity<?> resourceNotFoundException(ValidationException ex, WebRequest request){//Response Entity , es response con http status
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.OK);
	}
}
