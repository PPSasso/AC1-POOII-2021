package com.ac1_individual.ac1.controllers;

import java.net.URI;

import com.ac1_individual.ac1.DTOs.EventUpdateDTO;
import com.ac1_individual.ac1.entity.Event;
import com.ac1_individual.ac1.services.EventService;
import com.ac1_individual.ac1.services.TicketService;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    TicketService ticketService;

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event eventIn)
    {
        Event newEvent = eventService.createEvent(eventIn);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newEvent.getId()).toUri();
        return ResponseEntity.created(uri).body(newEvent);
    }

    @PutMapping("{id}")
    public ResponseEntity<Event> updateEvent(@RequestBody EventUpdateDTO eventIn, @PathVariable long id)
    {
        Event event = eventService.updateEvent(eventIn, id);
        return ResponseEntity.ok().body(event);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable long id)
    {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Event> getEventById(@PathVariable long id)
    {
        Event event = eventService.getEventById(id);

        return ResponseEntity.ok().body(event);
    }
    
    @GetMapping
    public ResponseEntity<Page<Event>> getEvents(

        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
        @RequestParam(value = "name", defaultValue = "") String name,
        @RequestParam(value = "place", defaultValue = "") String place,
        @RequestParam(value = "startDate", defaultValue = "1000-01-01") String startDate,
        @RequestParam(value = "description", defaultValue = "") String description


    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);

        Page<Event> events = eventService.getEvents(pageRequest, name, place, startDate, description);

        return ResponseEntity.ok(events);

    }
}
