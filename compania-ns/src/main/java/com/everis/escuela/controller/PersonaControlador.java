package com.everis.escuela.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everis.escuela.dto.PersonaDTO;
import com.everis.escuela.dto.PersonaReducidaDTO;
import com.everis.escuela.entidad.Persona;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;
import com.everis.escuela.service.impl.PersonaServiceImpl;

@RestController
@RefreshScope
public class PersonaControlador {
	
	@Autowired
	private PersonaServiceImpl PersonaService;
	
	@Value("${igv}")
	private String igv;
	
	@RequestMapping("/Personas")
	public List<PersonaDTO> getPersonas(){
		//definimos la variable de retorno
		List<PersonaDTO> response = new ArrayList<>();
		//companias del service
		Iterable<Persona> listado = PersonaService.obtenerPersonas();
		//definimos la instancia del modelmapper
		ModelMapper modelMapper = new ModelMapper();
		//recorro mis listado y agrego a mi variable de retorno
		listado.forEach(persona -> {
			response.add(modelMapper.map(persona, PersonaDTO.class));
		});
		
		return response;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value="/Personas", method = RequestMethod.POST)
	public PersonaDTO savePersona(
		@Valid @RequestBody PersonaReducidaDTO persona) throws Exception {
		ModelMapper modelMapper = new ModelMapper();
		PersonaDTO personaDTO=null;
		if(PersonaService.findByDni(persona.getDni())!=null) {
			throw new ValidationException("dni repetido");	
		}
		Persona personaResult = PersonaService.guardarPersona(modelMapper.map(persona, Persona.class));
		 personaDTO = modelMapper.map(personaResult, PersonaDTO.class);	
		return  personaDTO;
		
		 
	}
	
	@GetMapping("/Personas/{id}")
	public PersonaDTO findById(@PathVariable Long id) throws ResourceNotFoundException{
		ModelMapper modelMapper = new ModelMapper();
		PersonaDTO personaDTO = modelMapper.map(PersonaService.obtenerPersonaXId(id), PersonaDTO.class);
		return personaDTO;
	
	}
	
	@GetMapping("/igv")
	public String getIgv(){
		
		return "EL igv es:" + igv;
	
	}
	

}
