package com.nihil.planner.repositories;

import com.nihil.planner.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID>{

}
