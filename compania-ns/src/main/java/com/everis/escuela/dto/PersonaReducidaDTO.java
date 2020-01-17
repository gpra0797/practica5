package com.everis.escuela.dto;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonaReducidaDTO {	
	
	
	
	@NotNull
	@NotBlank
	@Size(max = 20)
	@JsonProperty(value="nombres")//para obtener la propiedad que se devuelve/envia desde el servidor en formato json
	private String nombres;
	
	@NotNull
	@NotBlank
	@Size(max = 50)
	@JsonProperty(value="apellido_paterno")
	private String apellido1;
	
	@Size(max = 50)
	@JsonProperty(value="apellido_materno")
	private String apellido2;
	
	
	@NotNull
	@NotBlank
	@Size(max = 8)
	@JsonProperty(value="dni")
	private String dni;

}
