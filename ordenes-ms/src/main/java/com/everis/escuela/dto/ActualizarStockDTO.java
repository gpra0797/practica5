package com.everis.escuela.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ActualizarStockDTO {
		
	private List<DetalleOrdenReducidaDTO> detalles;
}
