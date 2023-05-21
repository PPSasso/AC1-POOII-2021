package com.engSoft.ac2.application.dtos;

import com.engSoft.ac2.domain.model.Place;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PlaceCreationDTO{
    
 
    private String name;
    private String address;
    @JsonIgnore
    private Long idEvent;


    public PlaceCreationDTO(){

    }
    public PlaceCreationDTO(Place place) {
        this.name = place.getName();
        this.address = place.getAddress();
    }

    
    public Long getIdEvent() {
        return idEvent;
    }
    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
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

