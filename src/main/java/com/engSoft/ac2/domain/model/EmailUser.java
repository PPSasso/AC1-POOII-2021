package com.engSoft.ac2.domain.model;

import javax.validation.constraints.Email;

public class EmailUser{
    
    @Email(message = "FALHA")
    String emailFinal;

    public EmailUser(String email) {
        emailFinal = email;
    }

    public String getEmailFinal() {
        return emailFinal;
    }
    
}
