package com.engSoft.ac2.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engSoft.ac2.domain.model.Attend;

@Repository
public interface AttendRepository extends JpaRepository<Attend, Long> {
    
    public Page<Attend> findAll(Pageable pageRequest);
}
