package com.everis.producto.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.everis.producto.entity.Producto;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long>{

}
