package com.ac1_individual.ac1.controllers;

import java.net.URI;

import com.ac1_individual.ac1.entity.Attend;
import com.ac1_individual.ac1.services.AttendService;

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

@RestController
@RequestMapping("/attendees")
public class AttendController {
    
    @Autowired
    AttendService service;

    @GetMapping
    public ResponseEntity<Page<Attend>> getAttends(

        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy


    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);

        Page<Attend> attends = service.getAttends(pageRequest);

        return ResponseEntity.ok(attends);

    }

    @GetMapping("{id}")
    public ResponseEntity<Attend> getAttendById(@PathVariable long id)
    {
        Attend attend = service.getAttendById(id);

        return ResponseEntity.ok().body(attend);
    }

    @PostMapping
    public ResponseEntity<Attend> createEvent(@RequestBody Attend attendIn)
    {
        Attend newAttend = service.createAttend(attendIn);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newAttend.getId()).toUri();
        return ResponseEntity.created(uri).body(newAttend);
    }

    @PutMapping("{id}")
    public ResponseEntity<Attend> updateEvent(@RequestBody Attend eventIn, @PathVariable long id)
    {
        Attend event = service.updateAttend(eventIn, id);
        return ResponseEntity.ok().body(event);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable long id)
    {
        service.deleteAttend(id);
        return ResponseEntity.noContent().build();
    }
}
