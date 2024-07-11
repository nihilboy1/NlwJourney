package com.nihil.planner.repositories;

import com.nihil.planner.entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID>{

    List<Participant> findByTripId(UUID tripId);
}
