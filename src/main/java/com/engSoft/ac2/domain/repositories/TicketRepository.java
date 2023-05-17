package com.engSoft.ac2.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engSoft.ac2.domain.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
}
