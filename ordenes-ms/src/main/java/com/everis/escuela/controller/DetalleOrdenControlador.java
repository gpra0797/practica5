package com.everis.escuela.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.escuela.dto.DetalleOrdenDTO;
import com.everis.escuela.entidad.DetalleOrden;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.service.impl.DetalleOrdenServiceImpl;

@RestController
@RefreshScope
public class DetalleOrdenControlador {
	
	@Autowired
	private DetalleOrdenServiceImpl DetalleOrdenService;
	
	
	
	@RequestMapping("/DetalleOrdens")
	public List<DetalleOrdenDTO> getDetalleOrdens(){
		//definimos la variable de retorno
		List<DetalleOrdenDTO> response = new ArrayList<>();
		//companias del service
		Iterable<DetalleOrden> listado = DetalleOrdenService.obtenerDetalleOrdens();
		//definimos la instancia del modelmapper
		ModelMapper modelMapper = new ModelMapper();
		//recorro mis listado y agrego a mi variable de retorno
		listado.forEach(DetalleOrden -> {
			response.add(modelMapper.map(DetalleOrden, DetalleOrdenDTO.class));
		});
		
		return response;
	}
	
	
	
	@GetMapping("/DetalleOrdens/{id}")
	public DetalleOrdenDTO findById(@PathVariable Long id) throws ResourceNotFoundException{
		ModelMapper modelMapper = new ModelMapper();
		DetalleOrdenDTO DetalleOrdenDTO = modelMapper.map(DetalleOrdenService.obtenerDetalleOrdenXId(id), DetalleOrdenDTO.class);
		return DetalleOrdenDTO;
	
	}
	
	

}
