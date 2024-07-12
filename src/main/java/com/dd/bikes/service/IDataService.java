package com.dd.bikes.service;

import java.util.List;
import java.util.Optional;

import com.dd.bikes.exception.BikeIdNotExist;
import com.dd.bikes.model.Bike;

public interface IDataService {

	public Bike addBike(Bike bike);

	public List<Bike> getBikeList();

	public Optional<Bike> getBikeById(Long bikeId);

	public Bike updateBike(Bike bike) throws BikeIdNotExist;

	public void deleteBike(Long id);
}
