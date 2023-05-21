package com.engSoft.ac2.application.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import com.engSoft.ac2.domain.model.Event;


public class EventDTO {

    private long id;
    
    private String name;
    
    private String description;
    
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private LocalTime startTime;
    
    private LocalTime endTime;

    private String emailContact;
    
    private Long amountFreeTickets;
    
    private Long amountPaidTickets;
    
    private Double ticketPrice;
    
    private Long adminId;
    
    private AdminDTO admin;
    

    public EventDTO(Event event){
        
        setId(event.getId());
        setName(event.getName());
        setDescription(event.getDescription());
        setStartDate(event.getStartDate());
        setEndDate(event.getEndDate());
        setStartTime(event.getStartTime());
        setEndTime(event.getEndTime());
        setEmailContact(event.getEmailContact());
        setAmountFreeTickets(event.getAmountFreeTickets());
        setAmountPaidTickets(event.getAmountPaidTickets());
        setAdminId(event.getAdmin().getId());
        setAdmin(new AdminDTO(event.getAdmin()));
    }

    
    public EventDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public AdminDTO getAdmin() {
        return admin;
    }

    public void setAdmin(AdminDTO admin) {
        this.admin = admin;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
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

    public void setEmailContact(String email) {
        this.emailContact = email;
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

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EventDTO eventDTO = (EventDTO) o;
    return Objects.equals(name, eventDTO.name) &&
           Objects.equals(description, eventDTO.description) &&
           Objects.equals(startDate, eventDTO.startDate) &&
           Objects.equals(endDate, eventDTO.endDate) &&
           Objects.equals(startTime, eventDTO.startTime) &&
           Objects.equals(endTime, eventDTO.endTime) &&
           Objects.equals(emailContact, eventDTO.emailContact);
}

@Override
public int hashCode() {
    return Objects.hash(name, description, startDate, endDate, startTime, endTime, emailContact);
}
    
}
