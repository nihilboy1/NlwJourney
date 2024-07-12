package com.nihil.planner.services;

import com.nihil.planner.dtos.ActivityBasicData;
import com.nihil.planner.dtos.ActivityId;
import com.nihil.planner.dtos.ActivityReqPayload;
import com.nihil.planner.dtos.ParticipantBasicData;
import com.nihil.planner.entities.Activity;
import com.nihil.planner.entities.Trip;
import com.nihil.planner.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService{

    @Autowired
    ActivityRepository activityRepository;

    public ActivityId registerActivity(ActivityReqPayload payload, Trip trip){
        Activity newActivity = new Activity(payload.title(), payload.occurs_at(), trip);
        activityRepository.save(newActivity);
        return new ActivityId(newActivity.getId());
    }


    public List<ActivityBasicData> getAllActivities(UUID tripId){
        return activityRepository.findByTripId(tripId).stream().map((activity) ->  new ActivityBasicData(activity.getId(), activity.getTitle(), activity.getOccursAt())).toList();

    }
}
