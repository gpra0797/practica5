package com.everis.escuela.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor//Define un constructor con todos los parámetros
@NoArgsConstructor
@Entity
public class Persona {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombres")
	private String nombres;

	@Column(name = "apellido1")
	private String apellido1;
	
	@Column(name = "apellido2")
	private String apellido2;
	
	
	@Column(name = "dni", unique = true)
	private String dni;
	
	@ManyToOne
	@JoinColumn(name = "compania_id")
	private Compania compania;
	
}
