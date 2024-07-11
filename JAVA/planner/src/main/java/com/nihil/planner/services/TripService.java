package com.nihil.planner.services;

import com.nihil.planner.dtos.TripId;
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

    public TripId create(Trip trip){
        tripRepository.save(trip);
        return new TripId(trip.getId());
    }

    public Optional<Trip> read(UUID tripId){
        return tripRepository.findById(tripId);
    }
}
