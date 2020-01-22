package com.everis.producto.controlador;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.everis.producto.dto.CantidadDTO;
import com.everis.producto.dto.ProductoDTO;
import com.everis.producto.dto.ProductoReducidoDTO;
import com.everis.producto.entity.ImagenProducto;
import com.everis.producto.entity.Producto;
import com.everis.producto.entity.TipoProducto;
import com.everis.producto.exceptions.ResourceNotFoundException;
import com.everis.producto.exceptions.ValidationException;
import com.everis.producto.repository.feign.StockClient;
import com.everis.producto.service.impl.FeignClientServiceImpl;
import com.everis.producto.service.impl.ProductoServiceImpl;

@RestController
@RefreshScope
public class ProductoControlador {
	
	@Autowired
	private ProductoServiceImpl productoService;
	
	
	
	@Value("${igv}")
	private String igv;
	
	@Autowired
	private DiscoveryClient client;
	
	@Autowired
	private FeignClientServiceImpl stockClient;
	
	@GetMapping(value = "/productos")
	public List<ProductoDTO> obtenerProductos() {
		ModelMapper mapper = new ModelMapper();
		Iterable<Producto> productos = productoService.obtenerProductos();
		return StreamSupport.stream(productos.spliterator(), false)
					.map(p -> mapper.map(p, ProductoDTO.class))
					.collect(Collectors.toList());
	}
	
	@GetMapping(value = "/productos/{id}")
	public ProductoDTO obtenerProducto(
			@PathVariable("id") Long id
			) throws ResourceNotFoundException, ValidationException{
		
		ModelMapper mapper = new ModelMapper();
		ProductoDTO producto =  mapper.map(productoService.obtenerProductoXId(id), ProductoDTO.class);
//		producto.setCantidadStock(getCantidad("almacen-ms",id).getCantidad());
		producto.setCantidadStock(stockClient.obtenerCantidadStockProducto(id).getCantidad());
		return producto;
	}
	
	@PostMapping(value = "/productos")
	public ProductoDTO guardarProducto(
			@RequestBody ProductoReducidoDTO producto
			) throws ValidationException,ResourceNotFoundException {
		ModelMapper mapper = new ModelMapper();
		Producto productoMapper = mapper.map(producto, Producto.class);
		
		TipoProducto tipoProducto = new TipoProducto();
		tipoProducto.setCodigo(producto.getCodigoProducto());
		productoMapper.setTipoProducto(tipoProducto);
		
		ImagenProducto imgProducto = new ImagenProducto();
		imgProducto.setRuta(producto.getRutaImagen());
		imgProducto.setRutaThumbnail(producto.getRutaThumbnail());
		productoMapper.setImagenProducto(imgProducto);
		
		
		return mapper.map(productoService.guardarProducto(productoMapper), ProductoDTO.class);
	}
	
	
	@GetMapping("/igv")
	public String getIgv(){
		
		return "EL igv es:" + igv;
	
	}
	
	public CantidadDTO getCantidad(String service,
			Long id) {//stock-ms
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
	
	
}
