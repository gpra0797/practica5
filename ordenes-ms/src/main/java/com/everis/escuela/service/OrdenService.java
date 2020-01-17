package com.everis.escuela.service;

import com.everis.escuela.entidad.Orden;
import com.everis.escuela.exceptions.ResourceNotFoundException;

public interface OrdenService {
	
	
	public Iterable<Orden> obtenerOrdens();// Iterable
	
	public Orden guardarOrden(Orden Orden);
	
	public Orden obtenerOrdenXId(Long id) throws ResourceNotFoundException;
	
	public void deleteOrdenXId(Long id);
	
	public boolean existsById(Long id);

}
