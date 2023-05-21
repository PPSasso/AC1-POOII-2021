package com.engSoft.ac2.domain.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.engSoft.ac2.application.dtos.AttendCreateDTO;
import com.engSoft.ac2.application.dtos.AttendDTO;
import com.engSoft.ac2.application.factory.UserFactory;
import com.engSoft.ac2.domain.model.Attend;
import com.engSoft.ac2.domain.repositories.AttendRepository;


@Service
public class AttendService {

    @Autowired
    AttendRepository repo;

    public Page<AttendDTO> getAttends(PageRequest pageRequest) {
        
        Page<Attend> pages = repo.findAll(pageRequest);

    List<AttendDTO> attendDTOs = pages.stream()
        .map(attend -> new AttendDTO(attend))
        .collect(Collectors.toList());

    Page<AttendDTO> adminDTOPage = new PageImpl<>(attendDTOs, pageRequest, pages.getTotalElements());

    return adminDTOPage;
    }

    public AttendDTO createAttend(AttendCreateDTO attendDto) {

        for(Attend a : repo.findAll()){
            if(a.getEmail().equalsIgnoreCase(attendDto.getEmail())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO DE ENTIDADE: O email informado ja está sendo utilizado.");
            }
        }

        Attend  newAttend = (Attend)UserFactory.createUser("attendee", attendDto.getName(),attendDto.getEmail(),null);
        repo.save(newAttend);

        return new AttendDTO(newAttend);
    }

    public AttendDTO updateAttend(Attend attendIn, long id) {

        try{
            Attend attend = repo.findById(id).get();
            attend.setName(attendIn.getName());
            attend.setEmail(attendIn.getEmail());

            repo.save(attend);
            
            return new AttendDTO(attend);
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

    public AttendDTO getAttendById(long id) {
        try{
            Attend attend = repo.findById(id).get();
            
            return new AttendDTO(attend);
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        } 
    }
    
}
