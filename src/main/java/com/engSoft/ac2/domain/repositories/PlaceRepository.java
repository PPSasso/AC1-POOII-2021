package com.engSoft.ac2.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engSoft.ac2.domain.model.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
 
    public Page<Place> findAll(Pageable pageRequest);
}
