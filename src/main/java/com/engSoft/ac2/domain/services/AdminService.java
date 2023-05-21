package com.engSoft.ac2.domain.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.engSoft.ac2.application.dtos.AdminCreateDTO;
import com.engSoft.ac2.application.dtos.AdminDTO;
import com.engSoft.ac2.application.factory.UserFactory;
import com.engSoft.ac2.domain.model.Admin;
import com.engSoft.ac2.domain.repositories.AdminRepository;

@Service
public class AdminService {

    @Autowired
    AdminRepository repo;

    public Page<AdminDTO> getAdmins(PageRequest pageRequest) {
        Page<Admin> pages = repo.findAll(pageRequest);
    
        List<AdminDTO> adminDTOs = pages.stream()
            .map(admin -> new AdminDTO(admin))
            .collect(Collectors.toList());
    
        Page<AdminDTO> adminDTOPage = new PageImpl<>(adminDTOs, pageRequest, pages.getTotalElements());
    
        return adminDTOPage;
    }
    

    public AdminDTO createAdmin(AdminCreateDTO adminDto) {
        for(Admin a : repo.findAll()){
            if(a.getEmail().equalsIgnoreCase(adminDto.getEmail())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO DE ENTIDADE: O email informado ja está sendo utilizado.");
            }
        }

        Admin newAdmin = (Admin)UserFactory.createUser("admin", adminDto.getName(),adminDto.getEmail(),adminDto.getPhoneNumber());
        
         repo.save(newAdmin);

         return new AdminDTO(newAdmin);
    }

    public AdminDTO updateAdmin(AdminCreateDTO adminIn, long id) {

        try{
            Admin admin = repo.findById(id).get();
            admin.setName(adminIn.getName());
            admin.setEmail(adminIn.getEmail());

            repo.save(admin);
            
            return new AdminDTO(admin);
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }
    }

    public void deleteAdmin(long id) {
        try{
            repo.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ERRO DE SQL: A entidade Admin está associada a um evento. Nao pode ser deletada no estado atual.");
        }
    }

    public AdminDTO getAdminById(long id) {
        try{
            Admin admin = repo.findById(id).get();
            return new AdminDTO(admin);
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        } 
    }
    
}
