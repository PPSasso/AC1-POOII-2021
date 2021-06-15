package com.ac1_individual.ac1.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ac1_individual.ac1.DTOs.TicketDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_TICKET")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    
    private TypeTicket type;
    private Instant date;
    private Double price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ATTEND_BASEUSER_ID")
    private Attend attend;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "EVENT_ID")
    private Event event;

    

    public Ticket() {
    }

    public Ticket(TicketDTO tDto, Attend attend, Event event) {
        this.type = tDto.getType();
        this.date = tDto.getDate();
        this.price = tDto.getPrice();
        this.attend = attend;
        this.event = event;
    }

    public TypeTicket getType() {
        return type;
    }

    public void setType(TypeTicket type) {
        this.type = type;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Attend getAttend() {
        return attend;
    }

    public void setAttend(Attend attend) {
        this.attend = attend;
    }

    public long getId() {
        return id;
    }
    
    
    public Instant getDate() {
        return date;
    }
    
    public void setDate(Instant date) {
        this.date = date;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
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
        Ticket other = (Ticket) obj;
        if (id != other.id)
            return false;
        return true;
    }
    
    
}
