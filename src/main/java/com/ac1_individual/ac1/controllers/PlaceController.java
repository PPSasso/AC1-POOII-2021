package com.ac1_individual.ac1.controllers;

import java.net.URI;

import com.ac1_individual.ac1.entity.Place;
import com.ac1_individual.ac1.services.PlaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class PlaceController {

    @Autowired
    PlaceService service;

    @GetMapping
    public ResponseEntity<Page<Place>> getPaces(

        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy


    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);

        Page<Place> places = service.getPlaces(pageRequest);

        return ResponseEntity.ok(places);

    }
    
    @PostMapping
    public ResponseEntity<Place> createEvent(@RequestBody Place PlaceIn)
    {
        Place newPlace = service.createPlace(PlaceIn);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPlace.getId()).toUri();
        return ResponseEntity.created(uri).body(newPlace);
    }

    @PutMapping("{id}")
    public ResponseEntity<Place> updateEvent(@RequestBody Place eventIn, @PathVariable long id)
    {
        Place event = service.updatePlace(eventIn, id);
        return ResponseEntity.ok().body(event);
    }
}
