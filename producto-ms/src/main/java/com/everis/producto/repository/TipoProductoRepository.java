package com.everis.producto.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.everis.producto.entity.TipoProducto;

@Repository
public interface TipoProductoRepository extends CrudRepository<TipoProducto, Long>{

	public Optional<TipoProducto> findByCodigo(String codigo);
}
