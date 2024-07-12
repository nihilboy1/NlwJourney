package com.nihil.planner.dtos;

import java.util.UUID;

public record ParticipantBasicData(UUID participantId, String name, String email, Boolean isConfirmed){
}
