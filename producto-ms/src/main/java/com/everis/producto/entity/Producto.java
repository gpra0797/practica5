package com.everis.producto.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="producto")
public class Producto {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="codigo", unique = true)
	private String codigo;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="precio")
	private BigDecimal precio;

	@ManyToOne(cascade = CascadeType.ALL)
	private TipoProducto tipoProducto;
	
	@OneToOne(cascade = CascadeType.ALL)
	private ImagenProducto imagenProducto;
	
	@Column(name="activo")
	private boolean activo;
	
	
}

