package com.engSoft.ac2.application.dtos;

import com.engSoft.ac2.domain.model.Place;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PlaceDTO{
    
 
    private long id;
    private String name;
    private String address;
    @JsonIgnore
    private Long idEvent;


    public PlaceDTO(){

    }
    public PlaceDTO(Place place) {
        this.id = place.getId();
        this.name = place.getName();
        this.address = place.getAddress();
    }

    
    public Long getIdEvent() {
        return idEvent;
    }
    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }    
}

