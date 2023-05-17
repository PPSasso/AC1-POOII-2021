package com.engSoft.ac2.application.dtos;

import java.util.ArrayList;
import java.util.List;

import com.engSoft.ac2.domain.model.Admin; 

public class AdminReadDTO {

    private long id;
    private String name;
    private String email;
    private String phoneNumber; 

    private List<EventCreateDTO> events = new ArrayList<>();
    

    public AdminReadDTO() {
    }
    
    public AdminReadDTO(Admin admin, List<EventCreateDTO> events) {
        setId(admin.getId());
        setName(admin.getName());
        setPhoneNumber(admin.getPhoneNumber());
        setEvents(events);
    }

    public long getId() {
        return id;
    }



    public void setEvents(List<EventCreateDTO> events) {
        this.events = events;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }



    public AdminReadDTO(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<EventCreateDTO> getEvents() {
        return events;
    }

    public void addEvent(EventCreateDTO events) {
        this.events.add(events);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((events == null) ? 0 : events.hashCode());
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AdminReadDTO other = (AdminReadDTO) obj;
        if (events == null) {
            if (other.events != null)
                return false;
        } else if (!events.equals(other.events))
            return false;
        if (phoneNumber == null) {
            if (other.phoneNumber != null)
                return false;
        } else if (!phoneNumber.equals(other.phoneNumber))
            return false;
        return true;
    }
    
}
