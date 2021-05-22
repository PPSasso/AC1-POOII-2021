package com.ac1_individual.ac1.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "TB_EVENT")
public class Event implements Serializable{

    @OneToMany(mappedBy = "event")
    private List<Ticket> tickets = new ArrayList<>();
    
    @ManyToMany // (mappedBy = "events")
    private List<Place> places = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //Vai deletar isso daqui{
        private String place;
        
        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }
    //}Até aqui
    @NotBlank(message = "ERRO - O preenchimento do campo 'name' e obrigatorio!")
    private String name;

    @NotBlank(message = "ERRO - O preenchimento do campo 'description' e obrigatorio!")
    private String description;

    @NotNull(message = "ERRO - O preenchimento do campo 'startDate' e obrigatorio!")
    private LocalDate startDate;

    @NotNull(message = "ERRO - O preenchimento do campo 'startDate' e obrigatorio!")
    private LocalDate endDate;
    
    @NotNull(message = "ERRO - O preenchimento do campo 'startDate' e obrigatorio!")
    private LocalTime startTime;  
    
    @NotNull(message = "ERRO - O preenchimento do campo 'startDate' e obrigatorio!")
    private LocalTime endTime;
    
    @NotBlank(message = "ERRO - O preenchimento do campo 'emailContact' e obrigatorio!")
    private String emailContact;
    
    @NotNull(message = "ERRO - O preenchimento do campo 'amountFreeTickets' e obrigatorio!")
    private Long amountFreeTickets;
    
    @NotNull(message = "ERRO - O preenchimento do campo 'amountPaidTickets' e obrigatorio!")
    private Long amountPaidTickets;
    
    @NotNull(message = "ERRO - O preenchimento do campo 'ticketPrice' e obrigatorio!")
    private Double ticketPrice;
    
    @OneToOne
    private Admin admin;
    
    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
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