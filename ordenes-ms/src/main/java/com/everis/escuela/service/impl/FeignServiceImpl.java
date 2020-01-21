package com.everis.escuela.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.escuela.dto.CantidadDTO;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;
import com.everis.escuela.repository.feign.StockClient;
import com.everis.escuela.service.FeignService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class FeignServiceImpl implements FeignService {
	
	@Autowired
	private StockClient stockClient;
	
	@HystrixCommand(fallbackMethod = "metodoException")
	@Override
	public CantidadDTO obtenerCantidadStockProducto(Long id) throws ValidationException, ResourceNotFoundException {
		// TODO Auto-generated method stub
		return stockClient.obtenerCantidadXProducto(id);
	}
	
	public CantidadDTO metodoException(Long id) throws ValidationException, ResourceNotFoundException {
		// TODO Auto-generated method stub
		return new CantidadDTO(new BigDecimal(0));
	}

}
