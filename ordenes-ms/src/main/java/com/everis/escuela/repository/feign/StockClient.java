package com.everis.escuela.repository.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.everis.escuela.dto.CantidadDTO;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;


@FeignClient("almacen-ms" )
public interface StockClient {

	@GetMapping(value = "/cantidad/acumulado/producto/{idProducto}")
	public CantidadDTO obtenerCantidadXProducto(
			@PathVariable("idProducto") Long idProducto
			) throws ValidationException, ResourceNotFoundException;
	
	
	@PostMapping(value = "/stock/actualizar/{idProducto}/cantidad/{cantidad}")
	public void actualizarStock(
			@PathVariable("idProducto") Long idProducto,
			@PathVariable("cantidad") Double cantidad
			) throws ValidationException, ResourceNotFoundException;
}
