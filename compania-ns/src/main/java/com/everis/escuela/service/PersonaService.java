package com.everis.escuela.service;

import java.util.List;

import com.everis.escuela.entidad.Persona;
import com.everis.escuela.exceptions.ResourceNotFoundException;

public interface PersonaService {
	
	public List<Persona> obtenerPersonas();
	
	public Persona guardarPersona(Persona persona);
	
	public Persona obtenerPersonaXId(Long id) throws ResourceNotFoundException;
	
	public void deletePersonaXId(Long id);
	
	public boolean existsById(Long id);
	
	public Persona findByDni(String dni);
}
