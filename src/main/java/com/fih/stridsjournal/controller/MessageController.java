package com.fih.stridsjournal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {
	

	@GetMapping(value = "index")
	public String getDashboard() {
	
		return "index";
	}

	@GetMapping(value = "")
	public String getSocketPage() {
	
		return "subscribe";
	}
}
