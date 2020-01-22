package com.everis.escuela.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ActualizarStockDTO {
	@ApiModelProperty
	private List<DetalleOrdenReducidaDTO> detalles;
}
