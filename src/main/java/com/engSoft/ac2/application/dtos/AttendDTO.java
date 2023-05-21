package com.engSoft.ac2.application.dtos;

import com.engSoft.ac2.domain.model.Attend;

public class AttendDTO {
    
    private long id;
    private String name;
    private String email;
    private Double balance;
    
    public AttendDTO(Attend attend) {
        setId(attend.getId());
        setName(attend.getName());
        setEmail(attend.getEmail());
        setBalance(attend.getBalance());
    }

    public AttendDTO() {
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
