package com.engSoft.ac2.application.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.engSoft.ac2.application.dtos.AdminCreateDTO;
import com.engSoft.ac2.application.dtos.AdminDTO;
import com.engSoft.ac2.domain.services.AdminService;

@RestController
@RequestMapping("/admins")
public class AdminsController {
    
    @Autowired
    AdminService service;

    @GetMapping
    public ResponseEntity<Page<AdminDTO>> getAdmins(

        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy

    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);

        Page<AdminDTO> admins = service.getAdmins(pageRequest);

        return ResponseEntity.ok(admins);

    }

    @GetMapping("{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable long id)
    {
        AdminDTO admin = service.getAdminById(id);

        return ResponseEntity.ok().body(admin);
    }


    @PostMapping
    public ResponseEntity<AdminDTO> createEvent(@Validated @RequestBody AdminCreateDTO adminDto)
    {
        AdminDTO newAdmin = service.createAdmin(adminDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newAdmin.getId()).toUri();
        return ResponseEntity.created(uri).body(newAdmin);
    }

    @PutMapping("{id}")
    public ResponseEntity<AdminDTO> updateEvent(@Validated @RequestBody AdminCreateDTO eventIn, @PathVariable long id)
    {
        AdminDTO event = service.updateAdmin(eventIn, id);
        return ResponseEntity.ok().body(event);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable long id)
    {
        service.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
