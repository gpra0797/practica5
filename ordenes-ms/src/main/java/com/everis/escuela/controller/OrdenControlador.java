package com.everis.escuela.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.everis.escuela.dto.ActualizarStockDTO;
import com.everis.escuela.dto.CantidadDTO;
import com.everis.escuela.dto.DetalleOrdenReducidaDTO;
import com.everis.escuela.dto.OrdenDTO;
import com.everis.escuela.dto.OrdenReducidaDTO;
import com.everis.escuela.dto.ProductoDTO;
import com.everis.escuela.entidad.DetalleOrden;
import com.everis.escuela.entidad.Orden;
import com.everis.escuela.exceptions.ResourceNotFoundException;
import com.everis.escuela.exceptions.ValidationException;
import com.everis.escuela.repository.feign.ProductoClient;
import com.everis.escuela.repository.feign.StockClient;
import com.everis.escuela.service.impl.OrdenServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class OrdenControlador {
	
	@Autowired
	private OrdenServiceImpl OrdenService;
	
	@Autowired
	private DiscoveryClient client;
	
	@Autowired
	private ProductoClient productoClient;

	@Autowired
	private StockClient stockClient;
	
	@RequestMapping("/Ordens")
	public List<OrdenDTO> obtenerOrdens() {
		ModelMapper modelMapper = new ModelMapper();
		return StreamSupport.stream(OrdenService.obtenerOrdens().spliterator(), false)
				.map(c -> modelMapper.map(c, OrdenDTO.class)).collect(Collectors.toList());
	}
	
//	@HystrixCommand(fallbackMethod = "metodoException")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value="/Ordens", method = RequestMethod.POST)	
	public OrdenDTO saveOrden(
		@Valid	@RequestBody OrdenReducidaDTO orden) throws ValidationException, ResourceNotFoundException {		
		ModelMapper modelMapper = new ModelMapper();
		
		ProductoDTO productoDTO;
		CantidadDTO cantidadDTO;
		Double totalOrden=0.0;
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Orden or = modelMapper.map(orden, Orden.class);
		
		
		
		for(DetalleOrden detalle : or.getDetalleOrden()) {
			cantidadDTO =stockClient.obtenerCantidadXProducto(detalle.getIdProducto());
				
			if(detalle.getCantidad().doubleValue()>cantidadDTO.getCantidad().doubleValue()) {
				throw new ValidationException("La cantidad ingresada es mayor a la disponible");
			}
			
			
			
			productoDTO = productoClient.obtenerProducto(detalle.getIdProducto());
			detalle.setPrecio(productoDTO.getPrecio());
//			DetalleOrden detalleOrden = new DetalleOrden();
//			detalleOrden.setCantidad(cantidadDTO.getCantidad());
//			detalleOrden.setIdProducto(productoDTO.getId());
//			detalleOrden.setPrecio(productoDTO.getPrecio());
			totalOrden+= detalle.getCantidad().doubleValue() * productoDTO.getPrecio().doubleValue();
//			detalleOrden.setOrden(or);
//			or.addDetalle(detalleOrden);
			//detalle.setOrden(or);		
//			stockClient.actualizarStock(detalle.getIdProducto(),detalle.getCantidad().doubleValue());
		}
		or.setFechaEnvio(orden.getFechaEnvio());
		or.setIdCliente(orden.getIdCliente());
		or.setTotal(new BigDecimal(totalOrden));		
		Orden ordenGuardada = OrdenService.guardarOrden(or);
		
		ActualizarStockDTO actualizarStockDTO = new ActualizarStockDTO();
		actualizarStockDTO.setDetalles(new ArrayList<DetalleOrdenReducidaDTO>());
		for(DetalleOrden detalle : or.getDetalleOrden()) {
			DetalleOrdenReducidaDTO detalleOrdenReducidaDTO = new DetalleOrdenReducidaDTO(detalle.getIdProducto(),detalle.getCantidad());
			
			actualizarStockDTO.getDetalles().add(detalleOrdenReducidaDTO);
		}
		
		stockClient.actualizarStockskLista(actualizarStockDTO);
		
		
		
		 return  modelMapper.map(ordenGuardada, OrdenDTO.class);
	}
	
	@GetMapping("/Ordens/{id}")
	public Orden findById(@PathVariable Long id) throws ResourceNotFoundException{		
		return OrdenService.obtenerOrdenXId(id);
	
	}
	
	public CantidadDTO getCantidad(String service,
			Long id) {
		List<ServiceInstance> list = client.getInstances(service);
		if(list != null && list.size() >0) {
			int rand = (int)Math.round(Math.random()*10) % list.size() ;
			URI uri = list.get(rand).getUri();
			if(uri != null) {
				return (new RestTemplate()).getForObject(uri.toString()+"/cantidad/acumulado/producto/{idProducto}", CantidadDTO.class,id);
			}
		}
		return null;
	}
	
	public ProductoDTO getProductoDTO(String service,
			Long id) {
		List<ServiceInstance> list = client.getInstances(service);
		if(list != null && list.size() >0) {
			int rand = (int)Math.round(Math.random()*10) % list.size() ;
			URI uri = list.get(rand).getUri();
			if(uri != null) {
				return (new RestTemplate()).getForObject(uri.toString()+"/productos/{id}", ProductoDTO.class,id);
			}
		}
		return null;
	}

	public OrdenDTO metodoException(
			@Valid	@RequestBody OrdenReducidaDTO orden) throws ValidationException, ResourceNotFoundException {	
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(orden, OrdenDTO.class);
	}
	
	

}
