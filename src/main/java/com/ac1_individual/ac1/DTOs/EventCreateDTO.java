package com.ac1_individual.ac1.DTOs;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.ac1_individual.ac1.entity.Event;


public class EventCreateDTO {

    private long id;
    
    @NotBlank(message = "ERRO - O preenchimento do campo 'name' e obrigatorio!")
    private String name;
    
    @NotBlank(message = "ERRO - O preenchimento do campo 'description' e obrigatorio!")
    private String description;
    
    @NotNull(message = "ERRO - O preenchimento do campo 'startDate' e obrigatorio!")
    private LocalDate startDate;
    
    @NotNull(message = "ERRO - O preenchimento do campo 'endDate' e obrigatorio!")
    private LocalDate endDate;
    
    @NotNull(message = "ERRO - O preenchimento do campo 'startTime' e obrigatorio!")
    private LocalTime startTime;
    
    @NotNull(message = "ERRO - O preenchimento do campo 'endTime' e obrigatorio!")
    private LocalTime endTime;
    
    @NotBlank(message = "ERRO - O preenchimento do campo 'emailContact' e obrigatorio!")
    private String emailContact;
    
    @NotNull(message = "ERRO - O preenchimento do campo 'amountFreeTickets' e obrigatorio!")
    private Long amountFreeTickets;
    
    @NotNull(message = "ERRO - O preenchimento do campo 'amountPaidTickets' e obrigatorio!")
    private Long amountPaidTickets;
    
    @NotNull(message = "ERRO - O preenchimento do campo 'ticketPrice' e obrigatorio!")
    private Double ticketPrice;
    
    @NotNull(message = "ERRO - O preenchimento do campo 'adminId' e obrigatorio!")
    private Long adminId;
    
    private AdminDTO admin;
    

    public EventCreateDTO(Event event){
        
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
    }

    
    public EventCreateDTO() {
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
    
}
