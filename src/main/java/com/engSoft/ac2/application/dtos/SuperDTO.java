package com.engSoft.ac2.application.dtos;

import java.util.List;

public class SuperDTO {
    
    private List<TicketListDTO> ticketList;

    private Long amountFreeTickets;
    private Long amountPaidTickets;
    
    private Long amountSelledFreeTickets;
    private Long amountSelledPaidTickets;

    


    
    public SuperDTO(List<TicketListDTO> ticketList, Long amountFreeTickets, Long amountPaidTickets,
            Long amountSelledFreeTickets, Long amountSelledPaidTickets) 
    {
        this.ticketList = ticketList;
        this.amountFreeTickets = amountFreeTickets;
        this.amountPaidTickets = amountPaidTickets;
        this.amountSelledFreeTickets = amountSelledFreeTickets;
        this.amountSelledPaidTickets = amountSelledPaidTickets;
    }

    public SuperDTO() {
    }

    public List<TicketListDTO> getTicketList() {
        return ticketList;
    }
    
    public void setTicketList(List<TicketListDTO> ticketList) {
        this.ticketList = ticketList;
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

    public Long getAmountSelledFreeTickets() {
        return amountSelledFreeTickets;
    }

    public void setAmountSelledFreeTickets(Long amountSelledFreeTickets) {
        this.amountSelledFreeTickets = amountSelledFreeTickets;
    }

    public Long getAmountSelledPaidTickets() {
        return amountSelledPaidTickets;
    }

    public void setAmountSelledPaidTickets(Long amountSelledPaidTickets) {
        this.amountSelledPaidTickets = amountSelledPaidTickets;
    }
}
