package com.nihil.planner.repositories;

import com.nihil.planner.entities.Activity;
import com.nihil.planner.entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID>{

    List<Activity> findByTripId(UUID tripId);

}
