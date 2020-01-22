package com.everis.producto.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.producto.dto.CantidadDTO;
import com.everis.producto.exceptions.ResourceNotFoundException;
import com.everis.producto.exceptions.ValidationException;
import com.everis.producto.repository.feign.StockClient;
import com.everis.producto.service.FeignService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class FeignClientServiceImpl implements FeignService{

	@Autowired
	private StockClient stockClient;
	
	@HystrixCommand(fallbackMethod = "obtenerCantidadPorDefecto",
			commandKey = "obtenerCantidadStockProducto",
			groupKey = "obtenerCantidadStockProducto", threadPoolKey = "obtenerCantidadStockProducto",commandProperties = {
				@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
				@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5")
			})
	@Override
	public CantidadDTO obtenerCantidadStockProducto(Long id) throws ValidationException, ResourceNotFoundException {
		// TODO Auto-generated method stub
		
//		try {
//			Thread.sleep(4000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return stockClient.obtenerCantidadXProducto(id);
	}
	
	public CantidadDTO obtenerCantidadPorDefecto(Long id) throws ValidationException, ResourceNotFoundException {
		// TODO Auto-generated method stub
		return new CantidadDTO(new BigDecimal(0));
	}

}
