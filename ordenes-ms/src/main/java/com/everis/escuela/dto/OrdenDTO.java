package com.everis.escuela.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrdenDTO {
	@ApiModelProperty
	private Long id;
	
	private Long idCliente;
	
	
	private Date fecha;
	
	
	private Date fechaEnvio;
	
	private BigDecimal total;
	
	private List<DetalleOrdenDTO> detalleOrden;
	
}
