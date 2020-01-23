package com.everis.thymelead.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {
	
	@GetMapping("/orden")
	public String listarProductos(Model model )
	{	
		
		model.addAttribute("titulo","ORDENES");
		return "index";
	}
	
	
	@PostMapping("/guardarOrden")
	public String guardarOrden() {
		
		
		return "exito";
	}
	
	
	

}
