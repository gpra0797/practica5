package com.everis.escuela.entidad;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor//Define un constructor con todos los parámetros
@NoArgsConstructor
@Entity
public class Compania {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre")
	private String nombre;

	@Column(name = "ruc")
	private String ruc;
	
	@Column(name = "razon_social")
	private String razonSocial;
	
	@OneToMany(mappedBy = "compania", cascade = CascadeType.ALL)
	private List<Persona> personas;
	
	
	
}
