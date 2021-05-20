package com.ac1_individual.ac1.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ATTEND")
@PrimaryKeyJoinColumn(name = "BaseUser_ID")
public class Attend extends BaseUser {
    
    private Double balance;

    public Attend() {
    }

    public Attend(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
