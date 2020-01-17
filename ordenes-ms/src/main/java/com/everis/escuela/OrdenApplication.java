package com.everis.escuela;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.everis.escuela.util.CustomRepositoryImpl;

@EnableDiscoveryClient //usar los ids que muestra el eureka
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
@SpringBootApplication
public class OrdenApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdenApplication.class, args);
	}

}
