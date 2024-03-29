package com.engSoft.ac2.application.dtos;

import com.engSoft.ac2.domain.model.Attend;

public class AttendCreateDTO {
    
    private String name;
    private String email;
    private Double balance;
    
    public AttendCreateDTO(Attend attend) {
        setName(attend.getName());
        setEmail(attend.getEmail());
        setBalance(attend.getBalance());
    }

    public AttendCreateDTO() {
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
