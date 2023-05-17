package com.ac1_individual.ac1.domain.repositories;

import com.ac1_individual.ac1.domain.model.Admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    public Page<Admin> findAll(Pageable pageRequest);
}
