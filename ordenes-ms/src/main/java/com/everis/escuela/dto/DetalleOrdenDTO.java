package com.everis.escuela.dto;

import java.math.BigDecimal;

import com.everis.escuela.entidad.Orden;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetalleOrdenDTO {		
	
	private Long id;
	
	private Long idProducto;

	private Orden orden;
	
	
	private BigDecimal cantidad;
	
	
	private BigDecimal precio;
	
}
