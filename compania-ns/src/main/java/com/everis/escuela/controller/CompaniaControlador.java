package com.everis.escuela.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.websocket.server.PathParam;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everis.escuela.dto.CompaniaDTO;
import com.everis.escuela.dto.CompaniaReducidaDTO;
import com.everis.escuela.dto.PersonaDTO;
import com.everis.escuela.dto.PersonaReducidaDTO;
import com.everis.escuela.entidad.Compania;
import com.everis.escuela.entidad.Persona;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.service.PersonaService;
import com.everis.escuela.service.impl.CompaniaServiceImpl;
import com.everis.escuela.service.impl.PersonaServiceImpl;

@RestController
public class CompaniaControlador {
	
	@Autowired
	private CompaniaServiceImpl CompaniaService;
	
	@Autowired
	private PersonaServiceImpl personaService;

	
	@RequestMapping("/Companias")
	public List<CompaniaDTO> obtenerCompanias() {
		ModelMapper modelMapper = new ModelMapper();
		return StreamSupport.stream(CompaniaService.obtenerCompanias().spliterator(), false)
				.map(c -> modelMapper.map(c, CompaniaDTO.class)).collect(Collectors.toList());
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value="/Companias", method = RequestMethod.POST)	
	public Compania saveCompania(
			@RequestBody Compania Compania) {		
		 return  CompaniaService.guardarCompania(Compania);
	}
	
	@GetMapping("/Companias/{id}")
	public Compania findById(@PathVariable Long id) throws ResourceNotFoundException{		
		return CompaniaService.obtenerCompaniaXId(id);
	
	}
	

//	@PutMapping("/compania/{idCompania}/persona/{idPersona}")
//	public PersonaDTO findPersonaByIdAndCompania(
//			@PathVariable Long idCompania, 
//			@PathVariable Long idPersona ) {	
//		ModelMapper modelMapper = new ModelMapper();
//		PersonaDTO personaDTO=null;
//		Persona personaResult = CompaniaService.findPersonaByIdAndPersonaId(idCompania,idPersona);
//		 personaDTO = modelMapper.map(personaResult, PersonaDTO.class);	
//		return personaDTO;
//	
//	}
	
	@PutMapping("/compania/{idCompania}/persona/{idPersona}")
	public CompaniaDTO asociarCompania(
			@PathVariable Long idCompania, 
			@PathVariable Long idPersona ) throws ResourceNotFoundException {
			
	
		ModelMapper modelMapper = new ModelMapper();
		Compania compania = CompaniaService.asociarCompaniaPersona(idCompania, idPersona);
		return modelMapper.map(compania, CompaniaDTO.class);
		
		
	}
	
	
	@RequestMapping(value = "/Compania/{idCompania}", method = RequestMethod.PUT)
	public Compania updateCompania(@PathVariable Long idCompania,@RequestBody CompaniaReducidaDTO Compania) throws ResourceNotFoundException {	
		Compania c = CompaniaService.obtenerCompaniaXId(idCompania);
		c.setNombre(Compania.getNombre());
		c.setRazonSocial(Compania.getRazonSocial());
		c.setNombre(Compania.getNombre());
		c.setRuc(Compania.getRuc());
		c.setPersonas(null);
		 return  CompaniaService.guardarCompania(c);
	}

}
