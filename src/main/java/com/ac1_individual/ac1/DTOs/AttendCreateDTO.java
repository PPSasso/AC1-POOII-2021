package com.ac1_individual.ac1.DTOs;

import com.ac1_individual.ac1.entity.Attend;

public class AttendCreateDTO {
    
    private long id;
    private String name;
    private String email;
    private Double balance;
    
    public AttendCreateDTO(Attend attend) {
        setId(attend.getId());
        setName(attend.getName());
        setEmail(attend.getEmail());
        setBalance(attend.getBalance());
    }

    public AttendCreateDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
