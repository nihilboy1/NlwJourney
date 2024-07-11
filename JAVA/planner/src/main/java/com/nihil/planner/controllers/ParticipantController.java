package com.nihil.planner.controllers;

import com.nihil.planner.entities.Participant;
import com.nihil.planner.dtos.ParticipantReqPayload;
import com.nihil.planner.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController{

    @Autowired
    ParticipantService participantService;

    @PostMapping("/confirm/{id}")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantReqPayload payload){
        Optional<Participant> participant = participantService.read(id);
        if(participant.isPresent()){
            Participant rawParticipant = participant.get();
            rawParticipant.setConfirmed(true);
            rawParticipant.setName(payload.name());
            participantService.create(rawParticipant);
            return ResponseEntity.ok(rawParticipant);
        }

        return ResponseEntity.notFound().build();

    }
}
