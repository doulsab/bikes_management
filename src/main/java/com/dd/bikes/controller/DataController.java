package com.dd.bikes.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dd.bikes.model.Bike;
import com.dd.bikes.service.IDataService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DataController {

	private static final Logger logger = LogManager.getLogger(DataController.class);

	private IDataService bikeService;

	public DataController(IDataService bikeService) {
		this.bikeService = bikeService;
	}

	@PostMapping(value = "/addDetails", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bike> addBikeDetails(@RequestBody Bike bike) {
		Bike savedBike = bikeService.addBike(bike);
		logger.info("Data saved with id {} ", savedBike.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(savedBike);
	}

	@GetMapping(value = "/getBikesList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Bike>> getBikes() {
		return ResponseEntity.status(HttpStatus.OK).body(bikeService.getBikeList());
	}
	@GetMapping(value = "/getDetailsById/{bikeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Optional<Bike>> getByDetailsById(@PathVariable("bikeId") Long bikeId ) {
		Optional<Bike> bike = bikeService.getBikeById(bikeId);
		return ResponseEntity.status(HttpStatus.OK).body(bike);
	}
}

