package com.everis.escuela.entidad;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor//Define un constructor con todos los parámetros
@NoArgsConstructor
@Entity
public class DetalleOrden {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "id_producto")
	private Long idProducto;

	@ManyToOne
	@JoinColumn(name = "orden_id")
	private Orden orden;
	
	@Column(name = "cantidad")
	private BigDecimal cantidad;
	
	
	@Column(name = "precio")
	private BigDecimal precio;
	
}
