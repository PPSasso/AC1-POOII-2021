package com.ac1_individual.ac1.DTOs;

import com.ac1_individual.ac1.entity.Place;

public class PlaceDTO{
    
 
    long id;
    String name;
    String address;


    public PlaceDTO(){

    }
    public PlaceDTO(Place place) {
        this.id = place.getId();
        this.name = place.getName();
        this.address = place.getAddress();
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

