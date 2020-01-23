package com.everis.thymelead.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.FutureOrPresent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrdenReducidaDTO {
	private Long idCliente;

	private Date fecha;

	@FutureOrPresent
	private Date fechaEnvio;

	private BigDecimal total;

	private List<DetalleOrdenDTO> detalleOrden;
}
