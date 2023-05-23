package com.engSoft.ac2.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;

@Embeddable
public class EmailUser {

    @Email(message = "FALHA")
    @Column(name = "email")
    String emailFinal;

    public EmailUser() {
    }

    public EmailUser(String email) {
        emailFinal = email;
    }

    public String getEmailFinal() {
        return emailFinal;
    }

}
