package com.nihil.planner.controllers;

import com.nihil.planner.dtos.*;
import com.nihil.planner.entities.Link;
import com.nihil.planner.entities.Trip;
import com.nihil.planner.repositories.LinkRepository;
import com.nihil.planner.services.ActivityService;
import com.nihil.planner.services.LinkService;
import com.nihil.planner.services.ParticipantService;
import com.nihil.planner.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController{

    @Autowired
    ParticipantService participantService;

    @Autowired
    TripService tripService;

    @Autowired
    ActivityService activityService;

    @Autowired
    LinkService linkService;

    @PostMapping
    public ResponseEntity<TripId> createTrip(@RequestBody TripReqPayload payload){
        Trip newTrip = new Trip(payload);
        TripId tripId = tripService.create(newTrip);
        participantService.registerParticipantsToTrip(payload.emails_to_invite(), newTrip);
        return ResponseEntity.ok(tripId);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<Trip> getTrip(@PathVariable UUID tripId){
        Optional<Trip> trip = tripService.read(tripId);

        // Se 'trip' contém um valor 'tripInstance' (do tipo Trip), .map aplicará ResponseEntity::ok a esse valor.
        // Se 'trip.map(ResponseEntity::ok)' retorna um 'Optional' vazio, a lambda '() -> ResponseEntity.notFound()
        // .build()' é chamada, retornando uma resposta HTTP 404
        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/{tripId}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID tripId, @RequestBody TripReqPayload payload){
        Optional<Trip> trip = tripService.read(tripId);

        if(trip.isPresent()){
            Trip rawTrip = trip.get();
            rawTrip.setEndsAt(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
            rawTrip.setStartsAt(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            rawTrip.setDestination(payload.destination());
            tripService.create(rawTrip);
            return  ResponseEntity.ok(rawTrip);
        }

        return ResponseEntity.notFound().build();

    }

    @PutMapping("/confirm/{tripId}")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID tripId){
        Optional<Trip> trip = tripService.read(tripId);

        if(trip.isPresent()){
            Trip rawTrip = trip.get();
            rawTrip.setConfirmed(true);
            tripService.create(rawTrip);
            participantService.sendConfirmationEmailToParticipants(tripId);
            return  ResponseEntity.ok(rawTrip);
        }

        return ResponseEntity.notFound().build();

    }


    @PostMapping("/invite/{tripId}")
    public ResponseEntity<ParticipantId> inviteParticipant(@PathVariable UUID tripId, @RequestBody ParticipantReqPayload payload){
        Optional<Trip> trip = tripService.read(tripId);

        if(trip.isPresent()){
            Trip rawTrip = trip.get();
            ParticipantId participantId = participantService.registerParticipantToTrip(payload.email(), rawTrip);
            if(rawTrip.isConfirmed()) participantService.sendConfirmationEmailToParticipant(payload.email());
            return ResponseEntity.ok(participantId);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/participants/{tripId}")
    public ResponseEntity<List<ParticipantBasicData>> getAllParticipants(@PathVariable UUID tripId){
        List<ParticipantBasicData> participantBasicDataList = participantService.getAllParticipants(tripId);
        return ResponseEntity.ok(participantBasicDataList);
    }

    @PostMapping("/activities/{tripId}")
    public ResponseEntity<ActivityId> registerActivity(@PathVariable UUID tripId, @RequestBody ActivityReqPayload payload){
        Optional<Trip> trip = tripService.read(tripId);
        if(trip.isPresent()){
            Trip rawTrip = trip.get();
            ActivityId activityId = activityService.registerActivity(payload, rawTrip);
            return ResponseEntity.ok(activityId);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/activities/{tripId}")
    public ResponseEntity<List<ActivityBasicData>> getAllActivities(@PathVariable UUID tripId){
        List<ActivityBasicData> activityBasicDataList = activityService.getAllActivities(tripId);
        return ResponseEntity.ok(activityBasicDataList);
    }

    @PostMapping("/links/{tripId}")
    public ResponseEntity<LinkId> registerLink(@PathVariable UUID tripId, @RequestBody LinkReqPayload payload){
        Optional<Trip> trip = tripService.read(tripId);
        if(trip.isPresent()){
            Trip rawTrip = trip.get();
            LinkId linkId = linkService.registerLink(payload, rawTrip);
            return  ResponseEntity.ok(linkId);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/links/{tripId}")
    public ResponseEntity<List<LinkBasicData>> getAllLinks(@PathVariable UUID tripId){
        List<LinkBasicData> linkBasicData = linkService.getAllLinks(tripId);
        return ResponseEntity.ok(linkBasicData);
    }

}
