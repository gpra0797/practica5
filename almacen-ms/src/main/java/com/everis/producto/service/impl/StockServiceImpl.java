package com.everis.producto.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everis.producto.entity.Stock;
import com.everis.producto.exceptions.ResourceNotFoundException;
import com.everis.producto.repository.StockRepository;
import com.everis.producto.service.StockService;



@Transactional(readOnly = true)
@Service
public class StockServiceImpl implements StockService{
	
	@Autowired
	private StockRepository stockRepository;

	@Override
	public Stock guardarStock(Stock Stock) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock obtenerStockXId(Long id) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock> obtenerStocks() {
		// TODO Auto-generated method stub
	return StreamSupport.stream(stockRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public List<Stock> obtenerCantidadDeProductoEnTienda(Long idProducto, Long idTienda) {
		// TODO Auto-generated method stub
		return stockRepository.obtenerCantidadDeProductoEnTienda(idProducto, idTienda);
	}

	@Override
	public List<Stock> obtenerCantidadesXProducto(Long idProducto) {
		// TODO Auto-generated method stub
		return stockRepository.obtenerCantidadesXProducto(idProducto);
	}

	@Override
	public BigDecimal getCantidadXProducto(Long idProducto) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return stockRepository.getCantidadXProducto(idProducto)
				.orElseThrow(()-> new ResourceNotFoundException("no se encontró el producto en ninguna tienda "));
	}

	@Override
	public BigDecimal getCantidadXProductoAndTienda(Long idProducto, Long idTienda) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return  stockRepository.getCantidadXProductoAndTienda(idProducto, idTienda)
				.orElseThrow(()-> new ResourceNotFoundException("no se encontró el producto en esta tienda "));
	}

}
