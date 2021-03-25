package com.ac1_individual.ac1.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.ac1_individual.ac1.DTOs.EventDTO;
import com.ac1_individual.ac1.models.Event;
import com.ac1_individual.ac1.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventService {

    @Autowired
    EventRepository repo;

    public EventDTO createEvent(Event eventIn) {
        EventDTO entity = new EventDTO(eventIn);
        repo.save(eventIn);
        return entity;
    }

    public EventDTO updateEvent(EventDTO eventIn, long id) {
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

            return new EventDTO(event);
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }
    }
    
}
