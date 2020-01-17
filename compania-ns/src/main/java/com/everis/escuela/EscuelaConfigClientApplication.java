package com.everis.escuela;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.everis.escuela.util.CustomRepository;
import com.everis.escuela.util.CustomRepositoryImpl;

@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
@SpringBootApplication
public class EscuelaConfigClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(EscuelaConfigClientApplication.class, args);
	}

}
