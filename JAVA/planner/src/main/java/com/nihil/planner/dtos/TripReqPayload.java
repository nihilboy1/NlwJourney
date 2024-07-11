package com.nihil.planner.dtos;

import java.util.List;

public record TripReqPayload(String destination, String starts_at, String ends_at, List<String> emails_to_invite,
                             String owner_name, String owner_email){

}
