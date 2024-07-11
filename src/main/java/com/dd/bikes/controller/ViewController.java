package com.dd.bikes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	@GetMapping("/")
	public String dashboardPage() {
		return "dashboard";
	}

	@GetMapping("/login")
	public String indexPage() {
		return "index";
	}

	@GetMapping("/add")
	public String addDetails() {
		return "addBike";
	}

	@GetMapping("/dashboard")
	public String goToDashboard() {
		return "dashboard";
	}
}
