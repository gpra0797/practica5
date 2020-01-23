package com.everis.producto.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockDTO {		
	private Long id;
	
	private Long idTienda;
	
	private Long idProducto;
	
	private BigDecimal cantidad;
}

