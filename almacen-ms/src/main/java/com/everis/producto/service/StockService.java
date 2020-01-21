package com.everis.producto.service;

import java.math.BigDecimal;
import java.util.List;

import com.everis.producto.dto.ActualizarStockDTO;
import com.everis.producto.dto.CantidadDTO;
import com.everis.producto.entity.Stock;
import com.everis.producto.exceptions.ResourceNotFoundException;

public interface StockService {
	
	public List<Stock> obtenerStocks();
	
	public Stock guardarStock(Stock Stock);
	
	public Stock obtenerStockXId(Long id) throws ResourceNotFoundException;
	
	public List<Stock> obtenerCantidadDeProductoEnTienda(Long idProducto,Long idTienda);
	
	public List<Stock>  obtenerCantidadesXProducto(Long idProducto);
	
	
	BigDecimal getCantidadXProducto(Long idProducto) throws ResourceNotFoundException;
	
	BigDecimal getCantidadXProductoAndTienda(Long idProducto,Long idTienda) throws ResourceNotFoundException;
	
	void actualizarStockByIdProducto(Long idProducto,Double cantidad);
	
	void actualizarStockLista(List<Stock> lstStock);
}
