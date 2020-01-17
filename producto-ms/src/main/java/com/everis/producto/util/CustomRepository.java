package com.everis.producto.util;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean //para que no lo tenga en cuenta, esta clase necesita otros parametros de entidad, id
public interface CustomRepository<T , ID extends Serializable>  extends JpaRepository<T, ID>{
	
	void refresh(T t);
}
