package com.everis.producto.service;

import java.util.List;

import com.everis.producto.entity.Producto;
import com.everis.producto.exceptions.ResourceNotFoundException;

public interface ProductoService {
	
	public List<Producto> obtenerProductos();
	
	public Producto guardarProducto(Producto producto) throws ResourceNotFoundException;
	
	public Producto obtenerProductoXId(Long id) throws ResourceNotFoundException;
}
