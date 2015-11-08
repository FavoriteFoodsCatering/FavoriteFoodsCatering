package com.food.service.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@RequestMapping("/")
	public String home(){
		
		return "Your Biryani is On The Way";
	
	}

}
