package com.engSoft.ac2.application.factory;

import java.util.ArrayList;

import com.engSoft.ac2.domain.model.Admin;
import com.engSoft.ac2.domain.model.Attend;
import com.engSoft.ac2.domain.model.BaseUser;

public class UserFactory {
  public static BaseUser createUser(String userType, String name, String email, String phoneNumber) {
      if (userType.equalsIgnoreCase("admin")) {
        Admin admin = new Admin();
        admin.setName(name);
        admin.setEmail(email);
        admin.setPhoneNumber(phoneNumber);
          return admin;
      } else if (userType.equalsIgnoreCase("attendee")) {
        Attend attend = new Attend();
        attend.setName(name);
        attend.setEmail(email);
        attend.setBalance(0.0);
        attend.setTickets(new ArrayList<>());
          return attend;
      }
      return null;
  }
}