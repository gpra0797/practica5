package com.everis.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

@EnableZuulProxy
@SpringBootApplication
public class EscuelaGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(EscuelaGatewayApplication.class, args);
	}

}
