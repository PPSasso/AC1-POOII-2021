package com.engSoft.ac2.domain.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.engSoft.ac2.application.dtos.PlaceCreationDTO;
import com.engSoft.ac2.application.dtos.PlaceDTO;
import com.engSoft.ac2.domain.model.Place;
import com.engSoft.ac2.domain.repositories.PlaceRepository;

@Service
public class PlaceService {

    @Autowired
    PlaceRepository repo;

    public PlaceService(PlaceRepository repo) {
        this.repo = repo;
    }

    public Page<PlaceDTO> getPlaces(PageRequest pageRequest) {

        Page<Place> pages = repo.findAll(pageRequest);

        List<PlaceDTO> placeDtos = pages.stream()
                .map(place -> new PlaceDTO(place))
                .collect(Collectors.toList());

        Page<PlaceDTO> placeDTOPage = new PageImpl<>(placeDtos, pageRequest, pages.getTotalElements());

        return placeDTOPage;
    }

    public PlaceDTO createPlace(PlaceCreationDTO placeIn) {
        Place newPlace = repo.save(new Place(placeIn));
        return new PlaceDTO(newPlace);
    }

    public PlaceDTO updatePlace(PlaceCreationDTO placeIn, long id) {

        try {
            Place place = repo.findById(id).get();
            place.setName(placeIn.getName());
            place.setAddress(placeIn.getAddress());

            repo.save(place);

            return new PlaceDTO(place);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }
    }

    public void deletePlace(long id) {
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERRO DE ENTIDADE: A entidade nao foi encontrada.");
        }
    }

    public PlaceDTO getPlaceById(long id) {
        try {
            Place place = repo.findById(id).get();

            return new PlaceDTO(place);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ERRO DE ENTIDADE: A entidade Place nao foi encontrada.");
        }
    }

}