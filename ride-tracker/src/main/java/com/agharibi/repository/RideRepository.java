package com.agharibi.repository;

import java.util.List;

import com.agharibi.model.Ride;

public interface RideRepository {

	List<Ride> getRides();
	Ride createRide(Ride ride);
	Ride getRide(Integer id);
	Ride updateRide(Ride ride);
	void updateRides(List<Object[]> pairs);
	void deleteRide(Integer id);
	
}		