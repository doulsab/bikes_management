package com.dd.bikes.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dd.bikes.model.Bike;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DataController {

	@PostMapping("/addDetails")
	public Bike addBikeDetails(@RequestBody Bike bike) {
		System.out.println("Bike Data is " + bike);
		return null;

	}

}
