package com.ac1_individual.ac1.repositories;

import com.ac1_individual.ac1.entity.Place;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
 
    public Page<Place> findAll(Pageable pageRequest);
}
