package com.ac1_individual.ac1.DTOs;

import com.ac1_individual.ac1.entity.TypeTicket;

import java.time.Instant;

public class TicketDTO {



    private Long id;    
    private TypeTicket type;
    private Instant date;
    private Double price;

    private Long attendId;


    public TypeTicket getType() {
        return type;
    }

    public void setType(TypeTicket type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttendId() {
        return attendId;
    }

    public void setAttendId(Long attendId) {
        this.attendId = attendId;
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

}
