package com.ac1_individual.ac1.services;

import java.util.NoSuchElementException;

import com.ac1_individual.ac1.entity.Admin;
import com.ac1_individual.ac1.repositories.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminService {

    @Autowired
    AdminRepository repo;

    public Page<Admin> getAdmins(PageRequest pageRequest) {
        
        Page<Admin> pages = repo.findAll(pageRequest);

        return pages;
    }

    public Admin createAdmin(Admin adminIn) {

        return repo.save(adminIn);
    }

    public Admin updateAdmin(Admin adminIn, long id) {

        try{
            Admin admin = repo.findById(id).get();
            admin.setName(adminIn.getName());
            admin.setEmail(adminIn.getEmail());

            repo.save(admin);
            
            return admin;
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }
    }

    public void deleteAdmin(long id) {
        try{
            repo.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }
    }

    public Admin getAdminById(long id) {
        try{
            Admin admin = repo.findById(id).get();
            
            return admin;
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        } 
    }
    
}
