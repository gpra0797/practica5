package com.everis.escuela.exceptions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDetails {
	
	private Date fecha;
	private String message;
	private String detalle;
	
	
}
