package com.engSoft.ac2.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engSoft.ac2.domain.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    public Page<Admin> findAll(Pageable pageRequest);
}
