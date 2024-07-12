package com.nihil.planner.services;
import com.nihil.planner.dtos.*;
import com.nihil.planner.entities.Link;
import com.nihil.planner.entities.Trip;
import com.nihil.planner.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService{

    @Autowired
    LinkRepository linkRepository;


    public LinkId registerLink(LinkReqPayload payload, Trip trip){
        return new LinkId(linkRepository.save(new Link(payload.title(), payload.url(), trip)).getId());
    }


    public List<LinkBasicData> getAllLinks(UUID tripId){
        return linkRepository.findByTripId(tripId).stream().map((link) ->  new LinkBasicData(link.getId(),
                link.getTitle(), link.getUrl())).toList();

    }
}
