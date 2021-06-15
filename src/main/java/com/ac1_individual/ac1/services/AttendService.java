package com.ac1_individual.ac1.services;

import java.util.NoSuchElementException;

import com.ac1_individual.ac1.entity.Attend;
import com.ac1_individual.ac1.repositories.AttendRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AttendService {

    @Autowired
    AttendRepository repo;

    public Page<Attend> getAttends(PageRequest pageRequest) {
        
        Page<Attend> pages = repo.findAll(pageRequest);

        return pages;
    }

    public Attend createAttend(Attend attendIn) {

        for(Attend a : repo.findAll()){
            if(a.getEmail().equalsIgnoreCase(attendIn.getEmail())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO DE ENTIDADE: O email informado ja está sendo utilizado.");
            }
        }

        attendIn.setBalance(0.0);

        return repo.save(attendIn);
    }

    public Attend updateAttend(Attend attendIn, long id) {

        try{
            Attend attend = repo.findById(id).get();
            attend.setName(attendIn.getName());
            attend.setEmail(attendIn.getEmail());

            repo.save(attend);
            
            return attend;
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }
    }

    public void deleteAttend(long id) {
        try{
            repo.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }
    }

    public Attend getAttendById(long id) {
        try{
            Attend attend = repo.findById(id).get();
            
            return attend;
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        } 
    }
    
}
