package com.ac1_individual.ac1.repositories;

import com.ac1_individual.ac1.models.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e")
    public Page<Event> find(Pageable pageRequest);
    
}
