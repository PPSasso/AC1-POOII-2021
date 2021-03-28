package com.ac1_individual.ac1.services;

import javax.persistence.EntityNotFoundException;

import com.ac1_individual.ac1.DTOs.EventUpdateDTO;
import com.ac1_individual.ac1.models.Event;
import com.ac1_individual.ac1.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventService {

    @Autowired
    EventRepository repo;

    public EventUpdateDTO createEvent(Event eventIn) {
        EventUpdateDTO entity = new EventUpdateDTO(eventIn);
        repo.save(eventIn);
        return entity;
    }

    public EventUpdateDTO updateEvent(EventUpdateDTO eventIn, long id) {
        try{
            Event event = repo.getOne(id);
            event.setDescription(eventIn.getDescription());
            event.setPlace(eventIn.getPlace());
            event.setEmail(eventIn.getEmail());
            event.setStartDate(eventIn.getStartDate());
            event.setEndDate(eventIn.getEndDate());
            event.setStartTime(eventIn.getStartTime());
            event.setEndTime(eventIn.getEndTime());

            if(event.getStartDate().isBefore(event.getEndDate())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO DE DATA: Verifique as datas de inicio e fim do evento.");
            }
            else{
                repo.save(event);
            }

            return new EventUpdateDTO(event);
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }
    }

    public void deleteEvent(long id) {
        try{
            repo.deleteById(id);
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }
    }

    public Event getEventById(long id) {
        try{
            Event event = repo.getOne(id);
            
            return event;
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        } 
    }
    
}
