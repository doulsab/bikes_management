package com.dd.bikes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	@GetMapping("/")
	public String dashboardPage() {
		return "login";
	}

	@GetMapping("/login")
	public String indexPage() {
		return "login";
	}

	@GetMapping("/add")
	public String addDetails() {
		return "addBike";
	}

	@GetMapping("/dashboard")
	public String goToDashboard() {
		return "dashboard";
	}

	@GetMapping("/editBikeDetails")
	public String goToEditPage() {
		return "editBike";
	}

	@GetMapping("/addUser")
	public String addUserPage() {
		return "signupUser";
	}

	@GetMapping("/forgotpass")
	public String forgotPass() {
		return "forgotpass";
	}
	@GetMapping("/verifyopt")
	public String verifyOpt() {
		return "verifyopt";
	}
}
