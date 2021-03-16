package com.ac1_individual.ac1.controllers;

import java.net.URI;

import com.ac1_individual.ac1.DTOs.EventDTO;
import com.ac1_individual.ac1.models.Event;
import com.ac1_individual.ac1.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/Events")
public class EventController {

    @Autowired
    EventService service;

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody Event eventIn)
    {
        EventDTO newEvent = service.createEvent(eventIn);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newEvent.getId()).toUri();
        return ResponseEntity.created(uri).body(newEvent);
    }
    
}
