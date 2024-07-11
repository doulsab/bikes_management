package com.dd.bikes.service;

import java.util.List;

import com.dd.bikes.model.Bike;

public interface IDataService {

	public Bike addBike(Bike bike);

	public List<Bike> getBikeList();
}
