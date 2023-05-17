package com.ac1_individual.ac1.domain.repositories;

import com.ac1_individual.ac1.domain.model.BaseUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {
    
}
