package com.ac1_individual.ac1.application.dtos;

import com.ac1_individual.ac1.domain.model.Admin;

public class AdminDTO {

    private long id;
    private String name;
    private String email;
    private String phoneNumber; 

    public AdminDTO(Admin admin) {
        setId(admin.getId());
        setName(admin.getName());
        setEmail(admin.getEmail());
        setPhoneNumber(admin.getPhoneNumber()); 
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
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
