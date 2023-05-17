package com.engSoft.ac2.domain.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.engSoft.ac2.domain.model.Place;
import com.engSoft.ac2.domain.repositories.PlaceRepository;


@Service
public class PlaceService {

    @Autowired
    PlaceRepository repo;

    public Page<Place> getPlaces(PageRequest pageRequest) {
        
        Page<Place> pages = repo.findAll(pageRequest);

        return pages;
    }

    public Place createPlace(Place placeIn) {

        return repo.save(placeIn);
    }

    public Place updatePlace(Place placeIn, long id) {

        try{
            Place place = repo.findById(id).get();
            place.setName(placeIn.getName());
            place.setAddress(placeIn.getAddress());

            repo.save(place);
            
            return place;
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }
    }

    public void deletePlace(long id) {
        try{
            repo.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }
    }

    public Place getPlaceById(long id) {
        try{
            Place place = repo.findById(id).get();
            
            return place;
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade Place nao foi encontrada.");
        } 
    }

}