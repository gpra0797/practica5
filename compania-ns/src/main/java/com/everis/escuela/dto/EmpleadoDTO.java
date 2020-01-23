package com.everis.escuela.dto;

import java.util.Date;

import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO {
	
	private Long id;
	
	private String nombre;
	
	@JsonProperty(value = "apellido_paterno")
	private String apellidoPaterno;
	
	@JsonProperty(value = "apellido_materno")
	private String apellidoMaterno;
	
	private String dni;
	
	@JsonProperty(value = "fecha_ingreso")
	@PastOrPresent
	private Date fechaIngreso;
	
	private Boolean activo;
	
	private String empresa;
}
