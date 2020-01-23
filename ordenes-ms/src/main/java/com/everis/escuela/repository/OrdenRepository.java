package com.everis.escuela.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.everis.escuela.entidad.Orden;
import com.everis.escuela.util.CustomRepository;


@Repository
public interface OrdenRepository extends CrudRepository<Orden,Long> {

	@Query("select  orden from Orden orden where orden.fechaEnvio >= ?1")
	public List<Orden> obtenerOrdenesXfecha(Date fechaEnvio);
}
