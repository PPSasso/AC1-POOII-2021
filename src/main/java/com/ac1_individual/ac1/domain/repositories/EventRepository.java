package com.ac1_individual.ac1.domain.repositories;

import java.time.LocalDate;

import com.ac1_individual.ac1.domain.model.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e " +
           "WHERE " +
                " ( LOWER(e.name)           LIKE    LOWER(CONCAT('%', :name, '%')))        AND " +
                " ( LOWER(e.description)    LIKE    LOWER(CONCAT('%', :description, '%'))) AND " +          
                " ( e.startDate >= :startDate)                                           "
    )
    public Page<Event> find(Pageable pageRequest, String name, LocalDate startDate, String description);
    
}
