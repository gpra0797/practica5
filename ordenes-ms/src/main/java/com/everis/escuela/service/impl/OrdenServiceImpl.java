package com.everis.escuela.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everis.escuela.entidad.DetalleOrden;
import com.everis.escuela.entidad.Orden;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.repository.DetalleOrdenRepository;
import com.everis.escuela.repository.OrdenRepository;

import com.everis.escuela.service.OrdenService;

@Transactional(readOnly = true)
@Service
public class OrdenServiceImpl implements OrdenService{
	
	@Autowired		
	private OrdenRepository OrdenRepository;
	
	
		
	
	@Override
	public Iterable<Orden> obtenerOrdens() {		
	
		return OrdenRepository.findAll();
	}

	@Transactional(readOnly = false)
	@Override
	public Orden guardarOrden(Orden Orden) {
		return OrdenRepository.save(Orden);
	}

	@Override
	public Orden obtenerOrdenXId(Long id) throws ResourceNotFoundException {	
	
		 return OrdenRepository.findById(id)
				 .orElseThrow(()-> new ResourceNotFoundException(String.format("No se encontró el id %s en la bd.",id)));
		
	}

	
	@Override
	@Transactional(readOnly = false)
	public void deleteOrdenXId(Long id) {		
		 OrdenRepository.deleteById(id);
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return OrdenRepository.existsById(id);
	}

	@Override
	public List<Orden> obtenerOrdenesXfecha(String fechaEnvio) throws ParseException {
		// TODO Auto-generated method stub
		 Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(fechaEnvio);  
		return OrdenRepository.obtenerOrdenesXfecha(date1);
	}

	@Override
	public List<Orden> obtenerOrdenesXProducto(Long idProducto) {
		// TODO Auto-generated method stub
		return OrdenRepository.obtenerOrdenesXProducto(idProducto);
	}

	@Override
	public void deleteOrden(Long id) {
		// TODO Auto-generated method stub	
		
		 OrdenRepository.deleteById(id);;
	}

	@Override
	public Orden actualizarOrden(Orden orden) {
		// TODO Auto-generated method stub
		return null;
	}

	
	



	
}
