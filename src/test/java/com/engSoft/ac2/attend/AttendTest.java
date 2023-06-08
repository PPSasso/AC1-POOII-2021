package com.engSoft.ac2.attend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


import com.engSoft.ac2.domain.model.Attend;
import com.engSoft.ac2.domain.model.EmailUser;
import com.engSoft.ac2.domain.model.Ticket;

public class AttendTest {
    
    private Attend attend;
    
    @BeforeEach
    public void setUp() {
        List<Ticket> tickets = new ArrayList<>();
        attend = new Attend(1, "Pedro Paulo", "pedro.paulo@exemplo.com", 100.0, tickets);
    }
    
    @Test
    public void testGetId() {
        assertEquals(1, attend.getId());
    }
    
    @Test
    public void testGetName() {
        assertEquals("Pedro Paulo", attend.getName());
    }
    
    @Test
    public void testSetName() {
        attend.setName("Thiago Sanches");
        assertEquals("Thiago Sanches", attend.getName());
    }
    
    @Test
    public void testGetEmail() {
        assertEquals("pedro.paulo@exemplo.com", attend.getEmail());
    }
    
    @Test
    public void testSetEmail() {
        attend.setEmail("thiago.sanches@exemplo.com");
        assertEquals("thiago.sanches@exemplo.com", attend.getEmail());
    }

    @Test
    public void testInvalidEmail() {
        // Cria um objeto com um e-mail inválido
        attend.setEmail("thiago");

        System.out.println(attend.getEmail());

        // Cria um validador
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        // Valida o objeto e obtém as violações de validação
        Set<javax.validation.ConstraintViolation<EmailUser>> violations = validator.validate(attend.getEmailObj());

        // Verifica se existem violações de validação
        assertFalse(violations.isEmpty());
    }
    
    @Test
    public void testGetBalance() {
        assertEquals(100.0, attend.getBalance(), 0.01);
    }
    
    @Test
    public void testSetBalance() {
        attend.setBalance(200.0);
        assertEquals(200.0, attend.getBalance(), 0.01);
    }
    
    @Test
    public void testAddBalance() {
        attend.addBalance(50.0);
        assertEquals(150.0, attend.getBalance(), 0.01);
    }
    
    @Test
    public void testGetTickets() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());
        attend.setTickets(tickets);
        assertEquals(tickets, attend.getTickets());
    }
    
    @Test
    public void testAddTickets() {
        Ticket ticket = new Ticket();
        attend.addTickets(ticket);
        assertTrue(attend.getTickets().contains(ticket));
    }
    
    @Test
    public void testRefundTicket() {
        Ticket ticket = new Ticket();
        attend.addTickets(ticket);
        attend.refundTicket(ticket);
        assertFalse(attend.getTickets().contains(ticket));
    }
    
    @Test
    public void testEquals() {
        List<Ticket> tickets = new ArrayList<>();
        Attend otherAttend = new Attend(1, "Pedro Paulo", "pedro.paulo@exemplo.com", 100.0, tickets);
        assertTrue(attend.equals(otherAttend));
    }
    
    @Test
    public void testEqualsNotSameId() {
        List<Ticket> tickets = new ArrayList<>();
        Attend otherAttend = new Attend(2, "Pedro Paulo", "pedro.paulo@exemplo.com", 100.0, tickets);
        assertFalse(attend.equals(otherAttend));
    }
    
    @Test
    public void testHashCode() {
        List<Ticket> tickets = new ArrayList<>();
        Attend otherAttend = new Attend(1, "Pedro Paulo", "pedro.paulo@exemplo.com", 100.0, tickets);
        assertEquals(attend.hashCode(), otherAttend.hashCode());
    }
    
    @Test
    public void testHashCodeDifferentId() {
        List<Ticket> tickets = new ArrayList<>();
        Attend otherAttend = new Attend(2, "Pedro Paulo", "pedro.paulo@exemplo.com", 100.0, tickets);
        assertNotEquals(attend.hashCode(), otherAttend.hashCode());
    }
}

