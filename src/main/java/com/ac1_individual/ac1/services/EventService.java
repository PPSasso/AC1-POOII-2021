package com.ac1_individual.ac1.services;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import com.ac1_individual.ac1.DTOs.AdminDTO;
import com.ac1_individual.ac1.DTOs.EventCreateDTO;
import com.ac1_individual.ac1.DTOs.EventUpdateDTO;
import com.ac1_individual.ac1.DTOs.TicketDTO;
import com.ac1_individual.ac1.entity.Admin;
import com.ac1_individual.ac1.entity.Attend;
import com.ac1_individual.ac1.entity.Event;
import com.ac1_individual.ac1.entity.Place;
import com.ac1_individual.ac1.entity.Ticket;
import com.ac1_individual.ac1.entity.TypeTicket;
import com.ac1_individual.ac1.repositories.AdminRepository;
import com.ac1_individual.ac1.repositories.AttendRepository;
import com.ac1_individual.ac1.repositories.EventRepository;
import com.ac1_individual.ac1.repositories.PlaceRepository;
import com.ac1_individual.ac1.repositories.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepo;
    
    @Autowired
    PlaceRepository placeRepo;
    
    @Autowired
    AdminRepository adminRepo;
    
    @Autowired
    AttendRepository attendRepo;
    
    @Autowired
    TicketRepository ticketRepo;

    @Autowired
    PlaceService placeService;

    public EventCreateDTO createEvent(EventCreateDTO eventIn) {
        
        if(eventIn.getStartDate().isAfter(eventIn.getEndDate())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO DE DATA: Verifique as datas de inicio e fim do evento.");
        }
        else if(eventIn.getStartDate().isBefore(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO DE DATA: Nao e possivel cadastrar um evento no passado.");
        }
        else{

            try{
                Admin admin = adminRepo.findById(eventIn.getAdminId()).get();
                
                Event event = new Event(eventIn, admin);

                eventIn.setId(event.getId());
                
                eventRepo.save(event);
                
                eventIn.setAdmin(new AdminDTO(admin));
                eventIn.setId(event.getId());

                return eventIn;

            } catch(NoSuchElementException e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: Nao foi encontrado um admin com o ID informado.");
            }
            
        }

    }

    public Page<Event> getEvents(Pageable pageRequest, String name, String startDate, String description){
        LocalDate date = LocalDate.parse(startDate);
        Page<Event> pages = eventRepo.find(pageRequest, name, date, description);


        return pages;
    }

    public Event updateEvent(EventUpdateDTO eventIn, long id) {
        try{
            Event event = eventRepo.findById(id).get();
            event.setDescription(eventIn.getDescription());
            event.setEmailContact(eventIn.getEmailContact());
            event.setStartDate(eventIn.getStartDate());
            event.setEndDate(eventIn.getEndDate());
            event.setStartTime(eventIn.getStartTime());
            event.setEndTime(eventIn.getEndTime());
            event.setTicketPrice(eventIn.getTicketPrice());
            try{
                event.setAdmin(adminRepo.findById(eventIn.getAdminId()).get());
            } catch(NoSuchElementException e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: Nao foi encontrado um admin com o ID informado.");
            }

            if(event.getStartDate().isAfter(event.getEndDate())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO DE DATA: Verifique as datas de inicio e fim do evento.");
            }
            else if(event.getStartDate().isBefore(LocalDate.now())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO DE DATA: Nao e possivel alterar um evento no passado.");
            }
            else{
                eventRepo.save(event);
            }

            return event;
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }
    }

    public void deleteEvent(long id) {
        try{
            eventRepo.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }
    }

    public Event getEventById(long id) {
        try{
            Event event = eventRepo.findById(id).get();
            
            return event;
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        } 
    }

    public Event associatePlaceToEvent(Long idEvent, Long idPlace) {

        Event event = getEventById(idEvent);
        Place place = placeService.getPlaceById(idPlace);

        for(Event e : eventRepo.findAll()){
            if( 
               (event.getStartDate().isEqual(e.getStartDate())) //Se iniciar no mesmo dia.
            || (event.getStartDate().isEqual(e.getEndDate()))   //Se iniciar no mesmo dia que o outro acaba.
            || (event.getEndDate().isEqual(e.getStartDate()))   //Se acabar no mesmo dia que o outro inicia.
            || (event.getEndDate().isEqual(e.getEndDate()))     //Se acabar no mesmo dia.
            ){
                if( 
                       ( ( event.getStartTime().compareTo(e.getStartTime()) > 0 ) && ( event.getStartTime().compareTo(e.getEndTime()) < 0 ) ) //Se iniciar no intervalo de outro evento.
                    || ( ( event.getEndTime().compareTo(e.getStartTime()) > 0 ) && ( event.getEndTime().compareTo(e.getEndTime()) < 0 ) )     //Se acabar no intervalo de outro evento.
                    || ( event.getStartTime().compareTo(e.getStartTime()) == 0 )    //Se iniciar no mesmo horario
                    || ( event.getStartTime().compareTo(e.getEndTime()) == 0 )      //Se iniciar no horario de termino de outro evento
                    || ( event.getEndTime().compareTo(e.getStartTime()) == 0 )      //Se acabar no horario de inicio de outro evento
                    || ( event.getEndTime().compareTo(e.getEndTime()) == 0 )        //Se acabar no mesmo horario
                    || ( ( event.getStartTime().compareTo(e.getStartTime()) < 0 ) && ( event.getEndTime().compareTo(e.getEndTime()) > 0) )     //Se iniciar antes e acabar depois de um evento.
                    ){
                        for(Place p : e.getPlaces()){
                            if(p.equals(place)){
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO DE LOCAL: O local ja possui um evento neste horario e data.");
                            }
                        }
                    }    
            }
            else if( 
               ( (event.getStartDate().isAfter(e.getStartDate()) ) && (event.getStartDate().isBefore(e.getEndDate()) ) )    //Se iniciar no intervalo de outro evento.
            || ( (event.getEndDate().isAfter(e.getStartDate()) ) && (event.getEndDate().isBefore(e.getEndDate()) ) )        //Se acabar no intervalo de outro evento.
            || ( (event.getStartDate().isBefore(e.getStartDate()) ) && (event.getEndDate().isAfter(e.getEndDate()) ) )      //Se iniciar antes e acabar depois de um evento.
            ){
                for(Place p : e.getPlaces()){
                    if(p.equals(place)){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO DE LOCAL: O local ja possui um evento neste horario e data.");
                    }
                }
            }
        }

        event.addPlaces(place);

        place.addEvent(event);

        eventRepo.save(event);
        placeRepo.save(place);

        return event;
    }

    public void deletePlaceFromEvent(Long idEvent, Long idPlace) {
        Event event = getEventById(idEvent);
        placeService.getPlaceById(idPlace);

        Boolean flag = true;

        if(LocalDate.now().isAfter(event.getEndDate())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO: Nao e possivel deletar um evento passado.");
        }

        for(Place p : event.getPlaces()){
            if(p.getId() == idPlace){
                event.getPlaces().remove(p);
                flag = false;
            }
        }

        if(flag){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE BUSCA: O PLACE informado nao se encontra na lista deste EVENT.");
        }
    }

    public Ticket buyTicketFromEvent(Long idEvent, TicketDTO tDto){
        try{
            eventRepo.findById(idEvent).get();
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE BUSCA: O Evento informado nao foi encontrado.");
        }

        try{
            attendRepo.findById(tDto.getAttendId()).get();
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE BUSCA: O Attendee informado nao foi encontrado.");
        }

        Attend attend = attendRepo.findById(tDto.getAttendId()).get();
        Event event = eventRepo.findById(idEvent).get();
            
        Ticket ticket = new Ticket(tDto, attend, event);

        event.addTickets(ticket);
        attend.addTickets(ticket);

        eventRepo.save(event);
        attendRepo.save(attend);

        return ticket;

        // Usar cascade pra salvar o ticket sozinho? Ja fiz, testar pra ver se funfou.
        
    }

    public void ticketRefund(Long idEvent, Long idTicket) { //FALTA VALIDAR SE O EVENTO JA FOI OU NÃO! VER SE TA TUDO CERTINHO, FIZ SEM OLHAR!!
        try{
            Ticket ticket = ticketRepo.findById(idTicket).get();
            
            Event event = ticket.getEvent();
            Attend attend = ticket.getAttend();

            //Parte do Event
            event.refundTicket(ticket);

            //Parte do Attendee
            attend.refundTicket(ticket);
            if(ticket.getType() == TypeTicket.PAID){
                attend.setBalance(ticket.getPrice());
            }

            //Parte do Ticket
            ticketRepo.delete(ticket);

        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE BUSCA: O Ticket informado nao foi encontrado.");
        }
    }
    
}
