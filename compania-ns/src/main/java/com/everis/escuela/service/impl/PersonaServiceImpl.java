package com.everis.escuela.service.impl;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everis.escuela.entidad.Persona;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.repository.PersonaRepository;
import com.everis.escuela.service.PersonaService;

@Transactional(readOnly = true)
@Service
public class PersonaServiceImpl implements PersonaService{
	
	@Autowired		
	private PersonaRepository personaRepository;
	
	
	@Override		
	public List<Persona> obtenerPersonas() {		
//		return (List<Persona>) personaRepository.findAll();
		return StreamSupport.stream(personaRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = false)
	public Persona guardarPersona(Persona persona){
		Persona nuevo = personaRepository.save(persona);
		personaRepository.refresh(nuevo);
		return nuevo;
	}

	@Override
	public Persona obtenerPersonaXId(Long id) throws ResourceNotFoundException {	
	
		 return personaRepository.findById(id)
				 .orElseThrow(()-> new ResourceNotFoundException(String.format("No se encontró el id %s en la bd.",id)));
		
	}

	@Override
	public void deletePersonaXId(Long id) {		
		 personaRepository.deleteById(id);
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return personaRepository.existsById(id);
	}

	@Override
	public Persona findByDni(String dni) {
		 return personaRepository.findByDni(dni);
		}

}
