package com.engSoft.ac2.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.engSoft.ac2.application.dtos.PlaceCreationDTO;
import com.engSoft.ac2.application.dtos.PlaceDTO;

@Entity
@Table(name="TB_PLACE")
public class Place implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "ERRO - O preenchimento do campo 'Name' e obrigatorio!")
    private String name;
    @NotBlank(message = "ERRO - O preenchimento do campo 'Address' e obrigatorio!")
    private String address;

    @ManyToMany
    private List<Event> events = new ArrayList<>();

    public Place(PlaceCreationDTO placeCreationDTO){
        this.name = placeCreationDTO.getName();
        this.address = placeCreationDTO.getAddress();
    }
    public Place(){

    }
    public Place(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Place(PlaceDTO placeDTO) {
        this.id = placeDTO.getId();
        this.name = placeDTO.getName();
        this.address = placeDTO.getAddress();
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
    public List<Event> getEvents() {
        return events;
    }
    public void addEvent(Event event) {
        this.events.add(event);
    }
    public void removeEvent(Event event) {
        this.events.remove(event);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Place other = (Place) obj;
        if (id != other.id)
            return false;
        return true;
    }



    
}
