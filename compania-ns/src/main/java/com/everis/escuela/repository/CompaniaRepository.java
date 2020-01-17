package com.everis.escuela.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.everis.escuela.entidad.Compania;
import com.everis.escuela.entidad.Persona;


@Repository
public interface CompaniaRepository extends CrudRepository<Compania,Long> {
	
	@Query("Select persona from Persona persona where persona.id = ?1 and persona.compania.id = ?2")
	public Persona findPersonaByIdAndPersonaId(Long companiaId,Long personaId);
}
