package com.ac1_individual.ac1.repositories;

import java.time.LocalDate;

import com.ac1_individual.ac1.entity.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e " +
           "WHERE " +
                " ( e.name           LIKE CONCAT('%', :name, '%'))           AND " +
                " ( e.place       LIKE CONCAT('%', :place, '%'))             AND " +
                " ( e.startDate >= :startDate)                              AND " +
                " ( e.description    LIKE CONCAT('%', :description, '%'))        "           
    )
    public Page<Event> find(Pageable pageRequest, String name, String place, LocalDate startDate, String description);
    
}
