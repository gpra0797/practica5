package com.everis.escuela.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompaniaReducidaDTO {
		
	@JsonProperty(value="nombre")
	private String nombre;
	@JsonProperty(value="ruc")
	private String ruc;	
	@JsonProperty(value="razon_social")
	private String razonSocial;
	
	
	//private List<PersonaReducidaDTO> personas;
	
}
