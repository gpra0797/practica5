package com.everis.escuela.service;

import com.everis.escuela.dto.CantidadDTO;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;

public interface FeignService {

	public CantidadDTO obtenerCantidadStockProducto(Long id) throws ValidationException, ResourceNotFoundException;
}
