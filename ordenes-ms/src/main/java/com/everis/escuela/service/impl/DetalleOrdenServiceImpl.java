package com.everis.escuela.service.impl;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everis.escuela.entidad.DetalleOrden;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.repository.DetalleOrdenRepository;
import com.everis.escuela.service.DetalleOrdenService;

@Transactional(readOnly = true)
@Service
public class DetalleOrdenServiceImpl implements DetalleOrdenService{
	
	@Autowired		
	private DetalleOrdenRepository DetalleOrdenRepository;
	
	
	@Override		
	public List<DetalleOrden> obtenerDetalleOrdens() {		
		return StreamSupport.stream(DetalleOrdenRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = false)
	public DetalleOrden guardarDetalleOrden(DetalleOrden DetalleOrden){
		DetalleOrden nuevo = DetalleOrdenRepository.save(DetalleOrden);
		DetalleOrdenRepository.refresh(nuevo);
		return nuevo;
	}

	@Override
	public DetalleOrden obtenerDetalleOrdenXId(Long id) throws ResourceNotFoundException {	
	
		 return DetalleOrdenRepository.findById(id)
				 .orElseThrow(()-> new ResourceNotFoundException(String.format("No se encontró el id %s en la bd.",id)));
		
	}

	@Override
	public void deleteDetalleOrdenXId(Long id) {		
		 DetalleOrdenRepository.deleteById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return DetalleOrdenRepository.existsById(id);
	}

	

}
