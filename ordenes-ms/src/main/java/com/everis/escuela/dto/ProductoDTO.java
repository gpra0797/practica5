package com.everis.escuela.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductoDTO {		
	@ApiModelProperty
	private Long id;
	
	
	private String nombre;
	
	
	private String codigo;
	
	
	private String descripcion;
	
	
	private BigDecimal precio;
	
	private boolean activo;
	
	
	private BigDecimal cantidadStock;
}

