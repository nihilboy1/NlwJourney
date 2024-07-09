package com.nihil.planner.controllers;

import com.nihil.planner.entities.Trip;
import com.nihil.planner.payloads.TripCreateResponse;
import com.nihil.planner.payloads.TripRequest;
import com.nihil.planner.repositories.TripRepository;
import com.nihil.planner.services.ParticipantService;
import com.nihil.planner.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController{

    @Autowired
    ParticipantService participantService;

    @Autowired
    TripService tripService;

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequest payload){
        Trip newTrip = new Trip(payload);
        tripService.create(newTrip);
        participantService.registerParticipantsToEvent(payload.emails_to_invite(),newTrip.getId() );
        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<Trip> readTrip(@PathVariable UUID tripId){
        Optional<Trip> trip = tripService.read(tripId);

        // Se 'trip' contém um valor 'tripInstance' (do tipo Trip), .map aplicará ResponseEntity::ok a esse valor.
        // Se 'trip.map(ResponseEntity::ok)' retorna um 'Optional' vazio, a lambda '() -> ResponseEntity.notFound()
        // .build()' é chamada, retornando uma resposta HTTP 404
        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

}
