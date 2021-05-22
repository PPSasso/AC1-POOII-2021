package com.ac1_individual.ac1.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ADMIN")
@PrimaryKeyJoinColumn(name = "BaseUser_ID")
public class Admin extends BaseUser{
    
    private String phoneNumber; 

    @OneToMany(mappedBy = "admin")
    private List<Event> events = new ArrayList<>();
    

    public Admin() {
    }
    
    public Admin(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Admin(long id, String name, String email, String phoneNumber) {
        super(id, name, email);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event events) {
        this.events.add(events);
    }

    
}
