package com.everis.producto.service;

import com.everis.producto.dto.CantidadDTO;
import com.everis.producto.exceptions.ResourceNotFoundException;
import com.everis.producto.exceptions.ValidationException;

public interface FeignService {
	
	public CantidadDTO obtenerCantidadStockProducto(Long id) throws ValidationException, ResourceNotFoundException;
}
