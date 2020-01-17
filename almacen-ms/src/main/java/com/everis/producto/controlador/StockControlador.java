package com.everis.producto.controlador;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.everis.producto.dto.CantidadDTO;
import com.everis.producto.dto.StockDTO;
import com.everis.producto.entity.Stock;
import com.everis.producto.exceptions.ResourceNotFoundException;
import com.everis.producto.exceptions.ValidationException;
import com.everis.producto.service.impl.StockServiceImpl;

import net.bytebuddy.implementation.bytecode.Throw;



@RestController
@RefreshScope
public class StockControlador {
	
	@Autowired
	private StockServiceImpl StockService;
	
	@GetMapping(value = "/stock/acumulado/producto/{idProducto}")
	public String obtenerStocksXProducto(
			@PathVariable("idProducto") Long idProducto
			) throws ValidationException {
		String tiendasEncontradas="";
		Double doubleTotal=0.0;
		Iterable<Stock> lstStock = StockService.obtenerCantidadesXProducto(idProducto);
		if(StreamSupport.stream(lstStock.spliterator(), false).count()==0) {
			throw new ValidationException("No se encontró este producto en ninguna tienda");
		}
		for(Stock stock : lstStock) {
			doubleTotal += stock.getCantidad().doubleValue();
			if(!tiendasEncontradas.contains(String.valueOf(stock.getIdTienda()))) {
				tiendasEncontradas += String.valueOf(stock.getIdTienda()) + "|";
			}
			
		}
		return "La cantidad Total de productos encontrados es de "+ doubleTotal + " en las siguientes tiendas -> :" + tiendasEncontradas;

	}
	
	
	@GetMapping(value = "/Lista/stocks")
	public List<StockDTO> obtenerStocks(
			@PathVariable("id") Long id
			) {
		
		ModelMapper mapper = new ModelMapper();
		Iterable<Stock> Stocks = StockService.obtenerStocks();
		return StreamSupport.stream(Stocks.spliterator(), false)
					.map(p -> mapper.map(p, StockDTO.class))
					.collect(Collectors.toList());
	} 
	
	@GetMapping(value = "/Stocks/{id}")
	public StockDTO obtenerStock(
			@PathVariable("id") Long id
			) throws ResourceNotFoundException{
		
		ModelMapper mapper = new ModelMapper();
		return mapper.map(StockService.obtenerStockXId(id), StockDTO.class);
	}
	
		
	@GetMapping(value = "/stock/producto/{idProducto}/tienda/{idTienda}")
	public String obtenerCantidadDeProductoEnTienda(
			@PathVariable("idProducto") Long idProducto, 
			@PathVariable("idTienda") Long idTienda) throws ValidationException {
		String tiendasEncontradas="";
		Double doubleTotal=0.0;
		Iterable<Stock> lstStock = StockService.obtenerCantidadesXProducto(idProducto);
		if(StreamSupport.stream(lstStock.spliterator(), false).count()==0) {
			throw new ValidationException("No se encontró este producto en esta tienda");
		}
		for(Stock stock : StockService.obtenerCantidadDeProductoEnTienda(idProducto, idTienda)) {
			doubleTotal += stock.getCantidad().doubleValue();
			if(!tiendasEncontradas.contains(String.valueOf(stock.getIdTienda()))) {
				tiendasEncontradas += String.valueOf(stock.getIdTienda()) + "|";
			}
		}
		return "La cantidad de productos con el id |" + idProducto + "| En la tienda |" + idTienda + "| es de : " +  doubleTotal; 
	}
	
	@GetMapping(value = "/cantidad/acumulado/producto/{idProducto}")
	public CantidadDTO obtenerCantidadXProducto(
			@PathVariable("idProducto") Long idProducto
			) throws ValidationException, ResourceNotFoundException {
		BigDecimal cantidadAcumulada = new BigDecimal(0);
		cantidadAcumulada = StockService.getCantidadXProducto(idProducto);
	
		CantidadDTO cantidadDTO = new CantidadDTO();
		cantidadDTO.setCantidad(cantidadAcumulada);
		//cantidadDTO.setCantidad(new BigDecimal(2356));
		return cantidadDTO;
	}
	
	
	@GetMapping(value = "/cantidad/producto/{idProducto}/tienda/{idTienda}")
	public CantidadDTO obtenerCantidadXProductoEnTienda(
			@PathVariable("idProducto") Long idProducto, 
			@PathVariable("idTienda") Long idTienda) throws ValidationException, ResourceNotFoundException {
		BigDecimal doubleTotal = new BigDecimal(0);
		doubleTotal = StockService.getCantidadXProductoAndTienda(idProducto, idTienda);
	
		CantidadDTO cantidadDTO = new CantidadDTO();
		cantidadDTO.setCantidad(doubleTotal);
		
		return cantidadDTO;
	}
}
