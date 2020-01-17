package com.everis.producto.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everis.producto.entity.Producto;
import com.everis.producto.entity.TipoProducto;
import com.everis.producto.exceptions.ResourceNotFoundException;
import com.everis.producto.repository.ProductoRepository;
import com.everis.producto.repository.TipoProductoRepository;
import com.everis.producto.service.ProductoService;

@Transactional(readOnly = true)
@Service
public class ProductoServiceImpl implements ProductoService{
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private TipoProductoRepository tipoProductoRepository;
	
	@Override
	public List<Producto> obtenerProductos() {	
		return StreamSupport.stream(productoRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = false)
	public Producto guardarProducto(Producto producto) {
		
		TipoProducto tipoProducto = tipoProductoRepository.findByCodigo(producto.getTipoProducto().getCodigo()).get();
		
		producto.setTipoProducto(tipoProducto);
		
		
		return productoRepository.save(producto);
	}

	@Override
	public Producto obtenerProductoXId(Long id) throws ResourceNotFoundException {
		
		return productoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("no se encontró el producto con el id %s", id)));
	}

}
