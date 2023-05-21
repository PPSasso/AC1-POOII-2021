package com.engSoft.ac2.application.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import com.engSoft.ac2.application.dtos.PlaceCreationDTO;
import com.engSoft.ac2.application.dtos.PlaceDTO;
import com.engSoft.ac2.domain.services.PlaceService;

@RestController
@RequestMapping("/places")
public class PlaceController {

    @Autowired
    PlaceService service;

    @GetMapping
    public ResponseEntity<Page<PlaceDTO>> getPaces(

        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy


    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);

        Page<PlaceDTO> places = service.getPlaces(pageRequest);

        return ResponseEntity.ok(places);

    }
    
    @GetMapping("{id}")
    public ResponseEntity<PlaceDTO> getPlaceById(@PathVariable long id)
    {
        PlaceDTO place = service.getPlaceById(id);

        return ResponseEntity.ok().body(place);
    }

    @PostMapping
    public ResponseEntity<PlaceDTO> createPlace(@Validated @RequestBody PlaceCreationDTO PlaceIn)
    {
        PlaceDTO newPlace = service.createPlace(PlaceIn);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPlace.getId()).toUri();
        return ResponseEntity.created(uri).body(newPlace);
    }

    @PutMapping("{id}")
    public ResponseEntity<PlaceDTO> updateEvent(@RequestBody PlaceCreationDTO eventIn, @PathVariable long id)
    {
        PlaceDTO event = service.updatePlace(eventIn, id);
        return ResponseEntity.ok().body(event);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable long id)
    {
        service.deletePlace(id);
        return ResponseEntity.noContent().build();
    }
}
