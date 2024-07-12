package com.dd.bikes.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dd.bikes.exception.BikeIdNotExist;
import com.dd.bikes.model.Bike;
import com.dd.bikes.repository.BikeRepository;
import com.dd.bikes.service.IDataService;

@Service
public class DataService implements IDataService {
	private static final Logger logger = LogManager.getLogger(DataService.class);

	private BikeRepository bikeRepository;

	public DataService(BikeRepository bikeRepository) {
		this.bikeRepository = bikeRepository;
	}

	@Override
	public Bike addBike(Bike bike) {
		bike.setCreatedDate(new Date());
		bike = bikeRepository.save(bike);
		logger.info("Bike details stored successfully with id {}", bike.getId());
		return bike;
	}

	@Override
	public List<Bike> getBikeList() {
		return this.bikeRepository.findAll();
	}

	@Override
	public Optional<Bike> getBikeById(Long bikeId) {
		return this.bikeRepository.findById(bikeId);
	}

	@Override
	public Bike updateBike(Bike bike) throws BikeIdNotExist {
		Long bikeId = bike.getId();
		Optional<Bike> optionalBike = bikeRepository.findById(bikeId);

		if (optionalBike.isPresent()) {
			Bike existingBike = optionalBike.get();
			existingBike.setModel(bike.getModel());
			existingBike.setManufacturer(bike.getManufacturer());
			existingBike.setYear(bike.getYear());
			existingBike.setPrice(bike.getPrice());
			existingBike.setBikeType(bike.getBikeType());
			return bikeRepository.save(existingBike);
		} else {
			throw new BikeIdNotExist("Bike with IDnot found {}" + bikeId + " not found");
		}
	}

	@Override
	public void deleteBike(Long id) {
		bikeRepository.deleteById(id);		
	}

}
