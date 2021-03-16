package com.ac1_individual.ac1.DTOs;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ac1_individual.ac1.models.Event;

public class EventDTO {

    long id;
    String name;
    String description;
    String place;
    LocalDate startDate;
    LocalDate endDate;
    LocalTime startTime;
    LocalTime endTime;
    String email;
    public long getId() {
        return id;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public static EventDTO toDTO(Event eventIn) {
        EventDTO eventDTO = new EventDTO();

        eventDTO.id = eventIn.getId();
        eventDTO.name = eventIn.getName();
        eventDTO.description = eventIn.getDescription();
        eventDTO.place = eventIn.getPlace();
        eventDTO.startDate = eventIn.getStartDate();
        eventDTO.startTime = eventIn.getStartTime();
        eventDTO.endDate = eventIn.getEndDate();
        eventDTO.endTime = eventIn.getEndTime();
        eventDTO.email = eventIn.getEmail();

        return eventDTO;
    }
}
