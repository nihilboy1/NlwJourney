package com.nihil.planner.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityBasicData(UUID activityId, String title, LocalDateTime occursAt){
}
