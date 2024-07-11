package com.nihil.planner.services;

import com.nihil.planner.dtos.ParticipantBasicData;
import com.nihil.planner.dtos.ParticipantId;
import com.nihil.planner.entities.Participant;
import com.nihil.planner.entities.Trip;
import com.nihil.planner.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParticipantService{

    @Autowired
    ParticipantRepository participantRepository;

    public Optional<Participant> read(UUID id){
        return participantRepository.findById(id);
    }

    public void create(Participant participant){
        participantRepository.save(participant);
    }

    public void registerParticipantsToTrip(List<String> participantsToInvite, Trip trip){
        List<Participant> participantList =
                participantsToInvite.stream().map((email) -> new Participant(email, trip)).toList();
        participantRepository.saveAll(participantList);
        System.out.println(participantList.get(0).getId());

    }

    public List<ParticipantBasicData> getAllParticipants(UUID tripId){
        return participantRepository.findByTripId(tripId).stream().map((participant) ->  new ParticipantBasicData(participant.getId(), participant.getName(), participant.getEmail(), participant.isConfirmed())).toList();
    }

    public ParticipantId registerParticipantToTrip(String email, Trip trip){
        Participant participant = new Participant(email, trip);
        participantRepository.save(participant);
        return new ParticipantId(participant.getId());
    }


    public void sendConfirmationEmailToParticipants(UUID tripId){}

    public void sendConfirmationEmailToParticipant(String  email){}

}
