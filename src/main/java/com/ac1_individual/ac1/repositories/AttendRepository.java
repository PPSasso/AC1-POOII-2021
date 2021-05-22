package com.ac1_individual.ac1.repositories;

import com.ac1_individual.ac1.entity.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendRepository extends JpaRepository<Admin, Long> {
    
}
