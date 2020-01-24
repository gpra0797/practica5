package com.everis.escuela.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class OrdenControlador {

	@Autowired
	private OrdenServiceImpl OrdenService;

	@Autowired
	private DiscoveryClient client;

	@Autowired
	private ProductoClient productoClient;

//	@Autowired
//	private FeignServiceImpl stockClient;

	@Autowired
	private StockClient stockClientI;

	@RequestMapping("/Ordenes")
	public List<OrdenDTO> obtenerOrdens() {
		ModelMapper modelMapper = new ModelMapper();
		return StreamSupport.stream(OrdenService.obtenerOrdens().spliterator(), false)
				.map(c -> modelMapper.map(c, OrdenDTO.class)).collect(Collectors.toList());
	}

	@HystrixCommand(fallbackMethod = "metodoException", commandKey = "saveOrden", groupKey = "saveOrden", threadPoolKey = "saveOrden", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5") })
	@ApiOperation(value = "Guardar una orden de venta", notes = "Al guardar una orden se verificará el stock en los almacenes de cada producto", response = OrdenDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Se registró correctamente la orden", response = OrdenDTO.class),
			@ApiResponse(code = 404, message = "Recurso no encontrado", response = ResourceNotFoundException.class),
			@ApiResponse(code = 400, message = "Error de validación", response = ResourceNotFoundException.class),
			@ApiResponse(code = 200, message = "Validación de negocio", response = ValidationException.class), })
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/Ordens", method = RequestMethod.POST)
	public OrdenDTO saveOrden(@Valid @RequestBody OrdenReducidaDTO orden)
			throws ValidationException, ResourceNotFoundException {
		ModelMapper modelMapper = new ModelMapper();

		ProductoDTO productoDTO;
		CantidadDTO cantidadDTO;
		Double totalOrden = 0.0;

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Orden or = modelMapper.map(orden, Orden.class);

		for (DetalleOrden detalle : or.getDetalleOrden()) {

			cantidadDTO = stockClientI.obtenerCantidadXProducto(detalle.getIdProducto());

			if (detalle.getCantidad().doubleValue() > cantidadDTO.getCantidad().doubleValue()) {
				throw new ValidationException("La cantidad ingresada es mayor a la disponible");
			}

			productoDTO = productoClient.obtenerProducto(detalle.getIdProducto());
			detalle.setPrecio(productoDTO.getPrecio());

			totalOrden += detalle.getCantidad().doubleValue() * productoDTO.getPrecio().doubleValue();

		}
		or.setFechaEnvio(orden.getFechaEnvio());
		or.setIdCliente(orden.getIdCliente());
		or.setTotal(new BigDecimal(totalOrden));
		Orden ordenGuardada = OrdenService.guardarOrden(or);

		ActualizarStockDTO actualizarStockDTO = new ActualizarStockDTO();
		actualizarStockDTO.setDetalles(new ArrayList<DetalleOrdenReducidaDTO>());
		for (DetalleOrden detalle : or.getDetalleOrden()) {
			DetalleOrdenReducidaDTO detalleOrdenReducidaDTO = new DetalleOrdenReducidaDTO(detalle.getIdProducto(),
					detalle.getCantidad());

			actualizarStockDTO.getDetalles().add(detalleOrdenReducidaDTO);
		}

		stockClientI.actualizarStockskLista(actualizarStockDTO);

		return modelMapper.map(ordenGuardada, OrdenDTO.class);
	}

	@GetMapping("/Ordens/{id}")
	public Orden findById(@PathVariable Long id) throws ResourceNotFoundException {
		return OrdenService.obtenerOrdenXId(id);

	}
	
	
	@GetMapping("/orden/listado/{fechaEnvio}")
	public List<OrdenDTO> obtenerOrdenesXFecha(@PathVariable("fechaEnvio") String fechaEnvio) throws ParseException{
		ModelMapper modelMapper = new ModelMapper();
		return StreamSupport.stream(OrdenService.obtenerOrdenesXfecha((fechaEnvio)).spliterator(), false)
				.map(c -> modelMapper.map(c, OrdenDTO.class)).collect(Collectors.toList());
	}
	
	@GetMapping("/orden/detalle/{idProducto}")
	public List<OrdenDTO> obtenerOrdenesXProducto(@PathVariable("idProducto") Long idProducto) throws ParseException{
		ModelMapper modelMapper = new ModelMapper();
		return StreamSupport.stream(OrdenService.obtenerOrdenesXProducto((idProducto)).spliterator(), false)
				.map(c -> modelMapper.map(c, OrdenDTO.class)).collect(Collectors.toList());
	}
	

	public CantidadDTO getCantidad(String service, Long id) {
		List<ServiceInstance> list = client.getInstances(service);
		if (list != null && list.size() > 0) {
			int rand = (int) Math.round(Math.random() * 10) % list.size();
			URI uri = list.get(rand).getUri();
			if (uri != null) {
				return (new RestTemplate()).getForObject(uri.toString() + "/cantidad/acumulado/producto/{idProducto}",
						CantidadDTO.class, id);
			}
		}
		return null;
	}

	public ProductoDTO getProductoDTO(String service, Long id) {
		List<ServiceInstance> list = client.getInstances(service);
		if (list != null && list.size() > 0) {
			int rand = (int) Math.round(Math.random() * 10) % list.size();
			URI uri = list.get(rand).getUri();
			if (uri != null) {
				return (new RestTemplate()).getForObject(uri.toString() + "/productos/{id}", ProductoDTO.class, id);
			}
		}
		return null;
	}

	public OrdenDTO metodoException(@Valid @RequestBody OrdenReducidaDTO orden)
			throws ValidationException, ResourceNotFoundException {
		ModelMapper modelMapper = new ModelMapper();

		ProductoDTO productoDTO;
		CantidadDTO cantidadDTO;
		Double totalOrden = 0.0;

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Orden or = modelMapper.map(orden, Orden.class);

		for (DetalleOrden detalle : or.getDetalleOrden()) {
			cantidadDTO = new CantidadDTO(new BigDecimal(0));

			productoDTO = productoClient.obtenerProducto(detalle.getIdProducto());
			detalle.setPrecio(productoDTO.getPrecio());
			totalOrden += detalle.getCantidad().doubleValue() * productoDTO.getPrecio().doubleValue();

		}
		or.setFechaEnvio(orden.getFechaEnvio());
		or.setIdCliente(orden.getIdCliente());
		or.setTotal(new BigDecimal(totalOrden));
		Orden ordenGuardada = OrdenService.guardarOrden(or);
		ordenGuardada.setId(0L);
		return modelMapper.map(ordenGuardada, OrdenDTO.class);
	}
	
	@DeleteMapping("/orden/{idOrden}")
	public OrdenDTO deleteOrden(@PathVariable("idOrden") Long idOrden) throws ResourceNotFoundException, ValidationException {
		ModelMapper modelMapper = new ModelMapper();
		Orden orden = OrdenService.obtenerOrdenXId(idOrden);
		OrdenService.deleteOrdenXId(idOrden);
		ActualizarStockDTO actualizarStockDTO = new ActualizarStockDTO();
		actualizarStockDTO.setDetalles(new ArrayList<DetalleOrdenReducidaDTO>());
	
		
		for (DetalleOrden detalle : orden.getDetalleOrden()) {

			DetalleOrdenReducidaDTO detalleOrdenReducidaDTO = new DetalleOrdenReducidaDTO(detalle.getIdProducto(),
					detalle.getCantidad().negate());
	
			actualizarStockDTO.getDetalles().add(detalleOrdenReducidaDTO);
		}

		stockClientI.actualizarStockskLista(actualizarStockDTO);
		
		return modelMapper.map(orden, OrdenDTO.class);
	}
	
	@PutMapping("/orden/{idOrden}")
	public OrdenDTO actualizarFecha(@PathVariable("idOrden") Long idOrden, @RequestBody String fecha) throws ResourceNotFoundException, ParseException {
		Orden orden = OrdenService.obtenerOrdenXId(idOrden);
		Date fechaActualizada=new SimpleDateFormat("yyyy-MM-dd").parse(fecha);  
		orden.setFechaEnvio(fechaActualizada);		
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(OrdenService.guardarOrden(orden), OrdenDTO.class);
	}
	

}
