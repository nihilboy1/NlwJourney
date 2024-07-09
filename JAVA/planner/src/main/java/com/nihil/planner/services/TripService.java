package com.nihil.planner.services;

import com.nihil.planner.entities.Trip;
import com.nihil.planner.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TripService{

    @Autowired
    TripRepository tripRepository;

    public void create(Trip trip){
        tripRepository.save(trip);
    }

    public Optional<Trip> read(UUID tripId){
        return tripRepository.findById(tripId);
    }
}
