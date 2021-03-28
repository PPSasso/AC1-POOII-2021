package com.ac1_individual.ac1.controllers;

import java.net.URI;

import com.ac1_individual.ac1.DTOs.EventUpdateDTO;
import com.ac1_individual.ac1.models.Event;
import com.ac1_individual.ac1.services.EventService;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    EventService service;

    @PostMapping
    public ResponseEntity<EventUpdateDTO> createEvent(@RequestBody Event eventIn)
    {
        EventUpdateDTO newEvent = service.createEvent(eventIn);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newEvent.getId()).toUri();
        return ResponseEntity.created(uri).body(newEvent);
    }

    @PutMapping("{id}")
    public ResponseEntity<EventUpdateDTO> updateEvent(@RequestBody EventUpdateDTO eventIn, @PathVariable long id)
    {
        EventUpdateDTO dto = service.updateEvent(eventIn, id);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable long id)
    {
        service.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Event> getEventById(@PathVariable long id)
    {
        Event event = service.getEventById(id);

        return ResponseEntity.ok().body(event);

    }
    
}
