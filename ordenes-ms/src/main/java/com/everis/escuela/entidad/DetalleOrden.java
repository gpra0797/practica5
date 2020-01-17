package com.everis.escuela.entidad;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="detalle_orden")
public class DetalleOrden {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "id_producto")
	private Long idProducto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orden_id",insertable = 
			true)
	private Orden orden;
	
	@Column(name = "cantidad")
	private BigDecimal cantidad;
	
	
	@Column(name = "precio")
	private BigDecimal precio;
	
}
