package com.engSoft.ac2.application.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
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

import com.engSoft.ac2.application.dtos.EventAuxDTO;
import com.engSoft.ac2.application.dtos.EventCreateDTO;
import com.engSoft.ac2.application.dtos.EventDTO;
import com.engSoft.ac2.application.dtos.EventUpdateDTO;
import com.engSoft.ac2.application.dtos.SuperDTO;
import com.engSoft.ac2.application.dtos.TicketDTO;
import com.engSoft.ac2.domain.model.Event;
import com.engSoft.ac2.domain.model.Ticket;
import com.engSoft.ac2.domain.services.EventService;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventCreateDTO eventIn)
    {
        EventDTO newEvent = eventService.createEvent(eventIn);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newEvent.getId()).toUri();
        return ResponseEntity.created(uri).body(newEvent);
    }

    @PutMapping("{id}")
    public ResponseEntity<EventDTO> updateEvent(@RequestBody EventUpdateDTO eventIn, @PathVariable long id)
    {
        EventDTO event = eventService.updateEvent(eventIn, id);
        return ResponseEntity.ok().body(event);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable long id)
    {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable long id)
    {
        EventDTO event = eventService.getEventById(id);

        return ResponseEntity.ok().body(event);
    }
    
    @GetMapping
    public ResponseEntity<Page<EventDTO>> getEvents(

        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
        @RequestParam(value = "name", defaultValue = "") String name,
        @RequestParam(value = "startDate", defaultValue = "1000-01-01") String startDate,
        @RequestParam(value = "description", defaultValue = "") String description


    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);

        Page<EventDTO> events = eventService.getEvents(pageRequest, name, startDate, description);

        return ResponseEntity.ok(events);

    }

    @PostMapping("/{idEvent}/places/{idPlace}")
    public ResponseEntity<EventAuxDTO> associatePlaceToEvent(@PathVariable Long idEvent, @PathVariable Long idPlace){

        Event event = eventService.associatePlaceToEvent(idEvent, idPlace);
        EventAuxDTO dto = new EventAuxDTO(event, event.getAdmin());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(event.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);

    }

    @DeleteMapping("/{idEvent}/places/{idPlace}")
    public ResponseEntity<Void> deletePlaceFromEvent(@PathVariable Long idEvent, @PathVariable Long idPlace){
        eventService.deletePlaceFromEvent(idEvent, idPlace);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idEvent}/tickets")
    public ResponseEntity<Ticket> buyTicketFromEvent(@PathVariable Long idEvent,@Valid @RequestBody TicketDTO ticket){
        Ticket newTicket = eventService.buyTicketFromEvent(idEvent, ticket);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newTicket.getId()).toUri();

        return ResponseEntity.created(uri).body(newTicket);
    }

    @DeleteMapping("/{idEvent}/tickets/{idTicket}")
    public ResponseEntity<Void> ticketRefund(@PathVariable Long idEvent, @PathVariable Long idTicket){
        eventService.ticketRefund(idEvent, idTicket);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{idEvent}/tickets")
    public ResponseEntity<SuperDTO> getEventTickets(@PathVariable Long idEvent){
        
        SuperDTO superDTO = eventService.getEventTickets(idEvent);

        return ResponseEntity.ok(superDTO);

    }

}
