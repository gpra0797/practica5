package com.everis.escuela.service;

import com.everis.escuela.entidad.Compania;
import com.everis.escuela.entidad.Persona;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;

public interface CompaniaService {
	
	
	public Iterable<Compania> obtenerCompanias();// Iterable
	
	public Compania guardarCompania(Compania Compania);
	
	public Compania obtenerCompaniaXId(Long id) throws ResourceNotFoundException;
	
	public void deleteCompaniaXId(Long id);
	
	public boolean existsById(Long id);
	
	public Persona findPersonaByIdAndPersonaId(Long companiaId,Long personaId) throws ValidationException;
	
	public Compania asociarCompaniaPersona(Long companiaId,Long personaId) throws ResourceNotFoundException;
}
