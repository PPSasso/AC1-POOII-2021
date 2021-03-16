package com.ac1_individual.ac1.services;

import com.ac1_individual.ac1.DTOs.EventDTO;
import com.ac1_individual.ac1.models.Event;
import com.ac1_individual.ac1.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    EventRepository repo;

    public EventDTO createEvent(Event eventIn) {
        EventDTO entity = EventDTO.toDTO(eventIn);
        repo.save(eventIn);
        return entity;
    }
    
}
