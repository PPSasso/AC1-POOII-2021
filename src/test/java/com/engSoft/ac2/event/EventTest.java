package com.engSoft.ac2.event;

import com.engSoft.ac2.application.dtos.EventCreateDTO;
import com.engSoft.ac2.application.dtos.TicketDTO;
import com.engSoft.ac2.domain.model.Admin;
import com.engSoft.ac2.domain.model.Attend;
import com.engSoft.ac2.domain.model.Event;
import com.engSoft.ac2.domain.model.Place;
import com.engSoft.ac2.domain.model.Ticket;
import com.engSoft.ac2.domain.model.TypeTicket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventTest {

    private Admin admin;
    private Attend attend;

    @BeforeEach
    public void setup() {
        List<Ticket> tickets = new ArrayList<>();
        admin = new Admin(1, "Pedro Paulo", "pedro.paulo@exemplo.com", "123456789");
        attend = new Attend(1, "Pedro Paulo", "pedro.paulo@exemplo.com", 100.0, tickets);

    }

    @Test
    public void testConstructor() {
        EventCreateDTO eventCreateDTO = new EventCreateDTO();
        eventCreateDTO.setName("Test Event");
        eventCreateDTO.setDescription("Test Description");
        eventCreateDTO.setStartDate(LocalDate.now());
        eventCreateDTO.setEndDate(LocalDate.now().plusDays(1));
        eventCreateDTO.setStartTime(LocalTime.now());
        eventCreateDTO.setEndTime(LocalTime.now().plusHours(2));
        eventCreateDTO.setEmailContact("test@exemplo.com");
        eventCreateDTO.setAmountFreeTickets(100L);
        eventCreateDTO.setAmountPaidTickets(50L);
        eventCreateDTO.setTicketPrice(10.0);

        Event event = new Event(eventCreateDTO, admin);

        assertEquals(eventCreateDTO.getName(), event.getName());
        assertEquals(eventCreateDTO.getDescription(), event.getDescription());
        assertEquals(eventCreateDTO.getStartDate(), event.getStartDate());
        assertEquals(eventCreateDTO.getEndDate(), event.getEndDate());
        assertEquals(eventCreateDTO.getStartTime(), event.getStartTime());
        assertEquals(eventCreateDTO.getEndTime(), event.getEndTime());
        assertEquals(eventCreateDTO.getEmailContact(), event.getEmailContact());
        assertEquals(eventCreateDTO.getAmountFreeTickets(), event.getAmountFreeTickets());
        assertEquals(eventCreateDTO.getAmountPaidTickets(), event.getAmountPaidTickets());
        assertEquals(eventCreateDTO.getTicketPrice(), event.getTicketPrice(), 0.01);
        assertEquals(admin, event.getAdmin());
    }

    @Test
    public void testAddPlaces() {
        EventCreateDTO eventCreateDTO = new EventCreateDTO();
        eventCreateDTO.setName("Teste Event");
        eventCreateDTO.setDescription("Teste Description");
        eventCreateDTO.setStartDate(LocalDate.now());
        eventCreateDTO.setEndDate(LocalDate.now().plusDays(1));
        eventCreateDTO.setStartTime(LocalTime.now());
        eventCreateDTO.setEndTime(LocalTime.now().plusHours(2));
        eventCreateDTO.setEmailContact("teste@exemplo.com");
        eventCreateDTO.setAmountFreeTickets(100L);
        eventCreateDTO.setAmountPaidTickets(50L);
        eventCreateDTO.setTicketPrice(10.0);

        Event event = new Event(eventCreateDTO, admin);
        Place place1 = new Place(1,"test Place","Random Address");
        Place place2 = new Place(2,"Random Place","test Address");

        event.addPlaces(place1);
        event.addPlaces(place2);

        assertEquals(2, event.getPlaces().size());
        assertTrue(event.getPlaces().contains(place1));
        assertTrue(event.getPlaces().contains(place2));
    }

    @Test
    public void testAddTickets() {
        Event event = new Event();
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();

        event.addTickets(ticket1);
        event.addTickets(ticket2);

        assertEquals(2, event.getTickets().size());
        assertTrue(event.getTickets().contains(ticket1));
        assertTrue(event.getTickets().contains(ticket2));
    }

    @Test
    public void testRefundTicket() {
        Event event = new Event();

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setType(TypeTicket.PAID);
        ticketDTO.setDate(Instant.now());
        ticketDTO.setPrice(10.0);
        
        Ticket ticket1 = new Ticket(ticketDTO, attend, event);
        
        ticketDTO.setDate(Instant.now());
        ticketDTO.setPrice(30.0);
        
        Ticket ticket2 = new Ticket(ticketDTO, attend, event);

        event.addTickets(ticket1);
        event.addTickets(ticket2);

        event.refundTicket(ticket1);

        assertEquals(1, event.getTickets().size());
        for(Ticket ticket : event.getTickets()){
            assertFalse(ticket1==ticket);
            assertTrue(ticket2==ticket);
        }
    }
}

