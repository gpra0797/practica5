package com.everis.escuela.dto;

import java.math.BigDecimal;

import com.everis.escuela.entidad.Orden;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleOrdenReducidaDTO {
	@ApiModelProperty
	private Long idProducto;

	private BigDecimal cantidad;

}
