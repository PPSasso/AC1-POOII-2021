package com.engSoft.ac2.application.dtos;

import com.engSoft.ac2.domain.model.Admin;

public class AdminCreateDTO {

    private String name;
    private String email;
    private String phoneNumber; 



    public AdminCreateDTO() {}


    public AdminCreateDTO(Admin admin) {
        setName(admin.getName());
        setEmail(admin.getEmail());
        setPhoneNumber(admin.getPhoneNumber()); 
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
