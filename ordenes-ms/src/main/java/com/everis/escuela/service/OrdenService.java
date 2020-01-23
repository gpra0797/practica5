package com.everis.escuela.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.everis.escuela.entidad.Orden;
import com.everis.escuela.exceptions.ResourceNotFoundException;

public interface OrdenService {
	
	
	public Iterable<Orden> obtenerOrdens();// Iterable
	
	public Orden guardarOrden(Orden Orden);
	
	public Orden obtenerOrdenXId(Long id) throws ResourceNotFoundException;
	
	public void deleteOrdenXId(Long id);
	
	public boolean existsById(Long id);
	
	public List<Orden> obtenerOrdenesXfecha(String fecha) throws ParseException;

	//List<Orden> obtenerOrdenesXfecha(Date fechaEnvio);

}
