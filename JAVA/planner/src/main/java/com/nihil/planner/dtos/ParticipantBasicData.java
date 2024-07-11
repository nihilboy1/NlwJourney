package com.nihil.planner.dtos;

import java.util.UUID;

public record ParticipantBasicData(UUID id, String name, String email, Boolean isConfirmed){
}
