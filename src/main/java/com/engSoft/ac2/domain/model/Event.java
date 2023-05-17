package com.engSoft.ac2.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.engSoft.ac2.application.dtos.EventCreateDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_EVENT")
public class Event implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @OneToMany(mappedBy = "event")
    private List<Ticket> tickets = new ArrayList<>();
    
    @JsonIgnore
    @ManyToMany(mappedBy = "events")
    private List<Place> places = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="ADMIN_USER_ID")
    @NotNull(message = "ERRO - O preenchimento do campo 'adminId' e obrigatorio!")
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


    public Event(EventCreateDTO dto, Admin adm){
        setAdmin(adm);
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
    }

    public Event() {
    }

    public List<Place> getPlaces() {
        return places;
    }
    
    public void addPlaces(Place place) {
        this.places.add(place);
    }
    
    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    
    public void addTickets(Ticket ticket) {
        
        this.tickets.add(ticket);
    }

    public void refundTicket(Ticket ticket) {
        
        this.tickets.remove(ticket);
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
        Event other = (Event) obj;
        if (id != other.id)
            return false;
        return true;
    }
}