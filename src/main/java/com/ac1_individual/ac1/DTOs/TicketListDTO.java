package com.ac1_individual.ac1.DTOs;

import com.ac1_individual.ac1.entity.Ticket;
import com.ac1_individual.ac1.entity.TypeTicket;

public class TicketListDTO {
    
    private String name;
    private TypeTicket type;


    public TicketListDTO() {
    }

    public TicketListDTO(Ticket ticket) {
        this.name = ticket.getAttend().getName();
        this.type = ticket.getType();
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public TypeTicket getType() {
        return type;
    }
    public void setType(TypeTicket type) {
        this.type = type;
    }
}
