package com.agharibi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agharibi.model.Ride;
import com.agharibi.repository.RideRepository;

@Service("rideService")
public class RideServiceImpl implements RideService {

	@Autowired
	private RideRepository rideRepository;
	
	@Override
	public List<Ride> getRides() {
		return rideRepository.getRides();
	}
}
