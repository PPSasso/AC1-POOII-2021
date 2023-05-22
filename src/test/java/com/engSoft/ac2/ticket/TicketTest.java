package com.engSoft.ac2.ticket;

import com.engSoft.ac2.application.dtos.TicketDTO;
import com.engSoft.ac2.domain.model.Attend;
import com.engSoft.ac2.domain.model.Event;
import com.engSoft.ac2.domain.model.Ticket;
import com.engSoft.ac2.domain.model.TypeTicket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TicketTest {

    private Attend attend;
    private Event event;

    @BeforeEach
    public void setup() {
        List<Ticket> tickets = new ArrayList<>();
        attend = new Attend(1, "Pedro Paulo", "pedro.paulo@exemplo.com", 100.0, tickets);
        event = new Event();
    }

    @Test
    public void testConstructor() {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setType(TypeTicket.PAID);
        ticketDTO.setDate(Instant.now());
        ticketDTO.setPrice(10.0);

        Ticket ticket = new Ticket(ticketDTO, attend, event);

        assertEquals(ticketDTO.getType(), ticket.getType());
        assertEquals(ticketDTO.getDate(), ticket.getDate());
        assertEquals(ticketDTO.getPrice(), ticket.getPrice(), 0.01);
        assertEquals(attend, ticket.getAttend());
        assertEquals(event, ticket.getEvent());
    }

}
