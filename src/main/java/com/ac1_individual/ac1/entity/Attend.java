package com.ac1_individual.ac1.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ATTEND")
@PrimaryKeyJoinColumn(name = "ATTEND_BASEUSER_ID")
public class Attend extends BaseUser {
    
    private Double balance;

    @OneToMany(mappedBy = "attend")
    private List<Ticket> tickets = new ArrayList<>();

    public Attend() {
    }
    
    public Attend(long id, String name, String email,
            Double balance,
            List<Ticket> tickets) {
        super(id, name, email);
        this.balance = balance;
        this.tickets = tickets;
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

    public Double getBalance() {
        return balance;
    }

    public void addBalance(Double balance){
        this.balance += balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    
}
