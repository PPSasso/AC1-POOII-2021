package com.engSoft.ac2.application.dtos;

import java.time.Instant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.engSoft.ac2.domain.model.Ticket;
import com.engSoft.ac2.domain.model.TypeTicket;

public class TicketDTO {

    private TypeTicket type;
    private Instant date;
    private Double price;

    @NotBlank(message = "ERRO: E necessario passar o tipo do ticket!")
    private String typeTicket;

    @NotNull(message = "ERRO: E necessario passar o id de um attendee para realizar a operacao!")
    private Long attendId;


    public TicketDTO(Ticket t) {
        type = t.getType();
        date = t.getDate();
        price = t.getPrice();
        attendId = t.getAttend().getId();
    }

    

    public TicketDTO() {
    }



    public String getTypeTicket() {
        return typeTicket;
    }

    public void setTypeTicket(String typeTicket) {
        this.typeTicket = typeTicket;
    }

    public TypeTicket getType() {
        return type;
    }

    public void setType(TypeTicket type) {
        this.type = type;
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
