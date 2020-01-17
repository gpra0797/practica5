package com.everis.escuela.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.everis.escuela.entidad.Persona;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.util.CustomRepository;

@Repository
public interface PersonaRepository extends CustomRepository<Persona,Long> {
	
	
	public Persona findByDni(String dni);
	
}
