package com.everis.escuela.entidad;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor//Define un constructor con todos los parámetros
@NoArgsConstructor
@Entity
public class Orden {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "cliente_id")
	private Long idCliente;
	
	
	@Column(name = "fecha")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@Column(name = "fecha_envio")
	@Temporal(TemporalType.DATE)
	private Date fechaEnvio;
	
	@Column(name = "total")
	private BigDecimal total;
	
	
	@OneToMany(mappedBy = "orden",cascade = CascadeType.ALL)
	private List<DetalleOrden> detalleOrden;
	
	
	
	@PrePersist
    public void prePersist() {
        this.fecha = new Date();
    }
}
