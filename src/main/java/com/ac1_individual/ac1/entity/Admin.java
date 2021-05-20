package com.ac1_individual.ac1.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ADMIN")
@PrimaryKeyJoinColumn(name = "BaseUser_ID")
public class Admin extends BaseUser{
    
    private String phoneNumber; 

    public Admin() {
    }

    public Admin(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
