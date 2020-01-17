package com.everis.escuela.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonaDTO {		
	
	private Long id;
	
	@JsonProperty(value="nombres")//para obtener la propiedad que se devuelve/envia desde el servidor en formato json
	private String nombres;
	
	@JsonProperty(value="apellido_materno")
	private String apellido1;
	
	@JsonProperty(value="apellido_paterno")
	private String apellido2;
	
	@JsonProperty(value="dni")
	private String dni;
	
	private CompaniaReducidaDTO compania;
	
}
