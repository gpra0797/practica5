package com.everis.producto.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.everis.producto.entity.Stock;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long>{
	
	
	@Query("select  stock from Stock stock where stock.idProducto = ?1 and stock.idTienda = ?2 and stock.cantidad > 0 order by stock.cantidad asc")
	public List<Stock> obtenerCantidadDeProductoEnTienda(Long idProducto,Long idTienda);
	
	@Query("select stock from Stock stock where stock.idProducto = ?1")
	public List<Stock>  obtenerCantidadesXProducto(Long idProducto);
	
	@Query("select sum(stock.cantidad) from Stock stock where stock.idProducto = ?1")
	Optional<BigDecimal>  getCantidadXProducto(Long idProducto);
	
	
	@Query("select  sum(stock.cantidad) from Stock stock where stock.idProducto = ?1 and stock.idTienda = ?2")
	Optional<BigDecimal> getCantidadXProductoAndTienda(Long idProducto,Long idTienda);
}
