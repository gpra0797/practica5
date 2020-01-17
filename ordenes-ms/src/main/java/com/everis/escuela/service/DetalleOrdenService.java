package com.everis.escuela.service;

import java.util.List;

import com.everis.escuela.entidad.DetalleOrden;
import com.everis.escuela.exceptions.ResourceNotFoundException;

public interface DetalleOrdenService {
	
	public List<DetalleOrden> obtenerDetalleOrdens();
	
	public DetalleOrden guardarDetalleOrden(DetalleOrden DetalleOrden);
	
	public DetalleOrden obtenerDetalleOrdenXId(Long id) throws ResourceNotFoundException;
	
	public void deleteDetalleOrdenXId(Long id);
	
	public boolean existsById(Long id);
	}
