package com.ac1_individual.ac1.repositories;

import com.ac1_individual.ac1.entity.Attend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendRepository extends JpaRepository<Attend, Long> {
    
    public Page<Attend> findAll(Pageable pageRequest);
}
