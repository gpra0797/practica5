package com.everis.escuela.service.impl;

import javax.persistence.EntityManager;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everis.escuela.entidad.Compania;
import com.everis.escuela.entidad.Persona;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.repository.CompaniaRepository;
import com.everis.escuela.repository.PersonaRepository;
import com.everis.escuela.service.CompaniaService;

@Transactional(readOnly = true)
@Service
public class CompaniaServiceImpl implements CompaniaService{
	
	@Autowired		
	private CompaniaRepository CompaniaRepository;
	
	@Autowired		
	private PersonaRepository personaRepository;
	
		
	
	@Override
	public Iterable<Compania> obtenerCompanias() {		
	
		return CompaniaRepository.findAll();
	}

	@Transactional(readOnly = false)
	@Override
	public Compania guardarCompania(Compania Compania) {
		// TODO Auto-generated method stub
		return CompaniaRepository.save(Compania);
	}

	@Override
	public Compania obtenerCompaniaXId(Long id) throws ResourceNotFoundException {	
	
		 return CompaniaRepository.findById(id)
				 .orElseThrow(()-> new ResourceNotFoundException(String.format("No se encontró el id %s en la bd.",id)));
		
	}

	@Override
	public void deleteCompaniaXId(Long id) {		
		 CompaniaRepository.deleteById(id);
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return CompaniaRepository.existsById(id);
	}

	@Override
	public Persona findPersonaByIdAndPersonaId(Long companiaId, Long personaId) {
		
		return CompaniaRepository.findPersonaByIdAndPersonaId(companiaId, personaId);
	}

	@Override
	@Transactional(readOnly = false)
	public Compania asociarCompaniaPersona(Long companiaId, Long personaId) throws ResourceNotFoundException {
		Persona p = personaRepository.findById(personaId)
				.orElseThrow(()-> new ResourceNotFoundException(String.format("No se encontró la persona con el id %s indicado en la bd.",personaId)));
		
		Compania c = CompaniaRepository.findById(companiaId)
				.orElseThrow(()-> new ResourceNotFoundException(String.format("No se encontró la compania con el id %s indicado en la bd.",personaId)));
		
		p.setCompania(c);	
		personaRepository.save(p);
		
		return CompaniaRepository.findById(companiaId).get();
	}

	
}
