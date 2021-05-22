package com.ac1_individual.ac1.services;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import com.ac1_individual.ac1.DTOs.AdminDTO;
import com.ac1_individual.ac1.DTOs.EventCreateDTO;
import com.ac1_individual.ac1.DTOs.EventUpdateDTO;
import com.ac1_individual.ac1.entity.Admin;
import com.ac1_individual.ac1.entity.Event;
import com.ac1_individual.ac1.repositories.AdminRepository;
import com.ac1_individual.ac1.repositories.EventRepository;
import com.ac1_individual.ac1.repositories.PlaceRepository;

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

    public EventCreateDTO createEvent(EventCreateDTO eventIn) {
        
        if(eventIn.getStartDate().isAfter(eventIn.getEndDate())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO DE DATA: Verifique as datas de inicio e fim do evento.");
        }
        else{

            try{
                Admin admin = adminRepo.findById(eventIn.getAdminId()).get();
                
                Event event = new Event(eventIn, admin);
                
                eventRepo.save(event);
                
                eventIn.setAdmin(new AdminDTO(admin));
                eventIn.setId(event.getId());

                return eventIn;

            } catch(NoSuchElementException e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: Nao foi encontrado um admin com o ID informado.");
            }
            
        }

    }

    public Page<Event> getEvents(Pageable pageRequest, String name, String place, String startDate, String description){
        LocalDate date = LocalDate.parse(startDate);
        Page<Event> pages = eventRepo.find(pageRequest, name, place, date, description);

        return pages;
    }

    public Event updateEvent(EventUpdateDTO eventIn, long id) {
        try{
            Event event = eventRepo.findById(id).get();
            event.setDescription(eventIn.getDescription());
            event.setPlace(eventIn.getPlace());
            event.setEmailContact(eventIn.getEmail());
            event.setStartDate(eventIn.getStartDate());
            event.setEndDate(eventIn.getEndDate());
            event.setStartTime(eventIn.getStartTime());
            event.setEndTime(eventIn.getEndTime());
            event.setTicketPrice(eventIn.getTicketPrice());

            // try{
            //     event.addPlaces(placeRepo.findById(eventIn.getPlaceId()).get());
            // }catch(NoSuchElementException e){
            //     throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: O local do evento (place) não existe.");
            // }

            if(event.getStartDate().isAfter(event.getEndDate())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO DE DATA: Verifique as datas de inicio e fim do evento.");
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
    
}
