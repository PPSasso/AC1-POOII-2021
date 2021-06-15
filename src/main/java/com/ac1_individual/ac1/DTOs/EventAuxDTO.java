package com.ac1_individual.ac1.DTOs;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.ac1_individual.ac1.entity.Admin;
import com.ac1_individual.ac1.entity.Event;
import com.ac1_individual.ac1.entity.Place;
import com.ac1_individual.ac1.entity.Ticket;

public class EventAuxDTO implements Serializable{

    private long id;

    private List<Ticket> tickets = new ArrayList<>();
    
    private List<PlaceDTO> places = new ArrayList<>();
    
    private Admin admin;

    
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;  
    private LocalTime endTime;
    private String emailContact;
    private Long amountFreeTickets;
    private Long amountPaidTickets;
    private Double ticketPrice;


    public EventAuxDTO(Event dto, Admin adm){
        setAdmin(adm);
        setId(dto.getId());
        setName(dto.getName());
        setDescription(dto.getDescription());
        setEmailContact(dto.getEmailContact());
        setAmountFreeTickets(dto.getAmountFreeTickets());
        setAmountPaidTickets(dto.getAmountPaidTickets());
        setStartDate(dto.getStartDate());
        setEndDate(dto.getEndDate());
        setStartTime(dto.getStartTime());
        setEndTime(dto.getEndTime());
        setTicketPrice(dto.getTicketPrice());

        List<PlaceDTO> placeList = new ArrayList<>();

        for(Place e : dto.getPlaces()){
            PlaceDTO place = new PlaceDTO(e);
            placeList.add(place);
        }

        setTickets(dto.getTickets());
        setPlaces(placeList);

        //fazer um for each aqui pra lista de places e tickets pra transformar os dois pra DTO
    }

    public EventAuxDTO() {
    }

    public List<PlaceDTO> getPlaces() {
        return places;
    }
    
    
    public void setId(long id) {
        this.id = id;
    }

    public void setPlaces(List<PlaceDTO> places) {
        this.places = places;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    
    public Admin getAdmin() {
        return admin;
    }
    
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Long getAmountFreeTickets() {
        return amountFreeTickets;
    }
    public void setAmountFreeTickets(Long amountFreeTickets) {
        this.amountFreeTickets = amountFreeTickets;
    }
    public Long getAmountPaidTickets() {
        return amountPaidTickets;
    }
    public void setAmountPaidTickets(Long amountPaidTickets) {
        this.amountPaidTickets = amountPaidTickets;
    }
    public Double getTicketPrice() {
        return ticketPrice;
    }
    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
    public String getEmailContact() {
        return emailContact;
    }
    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }
}