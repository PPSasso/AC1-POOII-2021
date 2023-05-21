package com.engSoft.ac2.application.factory;

import com.engSoft.ac2.application.dtos.TicketDTO;
import com.engSoft.ac2.domain.model.Attend;
import com.engSoft.ac2.domain.model.Event;
import com.engSoft.ac2.domain.model.Ticket;

public class TicketFactory {
  public static Ticket createTicket(TicketDTO tDto, Attend attend, Event event){
    return new Ticket(tDto,attend,event);
  }
}
