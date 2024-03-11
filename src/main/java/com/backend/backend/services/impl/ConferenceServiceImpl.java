package com.backend.backend.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.backend.entities.Conference;
import com.backend.backend.payloads.ConferenceDTO;
import com.backend.backend.repositories.ConferenceRepo;
import com.backend.backend.services.ConferenceService;
import com.backend.backend.utils.ApiResponse;
import com.backend.backend.utils.Transformations;

import jakarta.persistence.PersistenceException;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    @Autowired
    private ConferenceRepo conferenceRepo;

    /**
     * Create a new conference
     * 
     * @param conferenceDto the conference to be created
     * 
     * @return ApiResponse with the result of the operation
     * 
     */
    @SuppressWarnings("null")
    @Override
    public ApiResponse createConference(ConferenceDTO conferenceDto) {
        if (conferenceDto == null) return new ApiResponse("Conference cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Conference conferenceEntity = Transformations.dtoToConference(conferenceDto);

            this.conferenceRepo.save(conferenceEntity);

            return new ApiResponse("Conference created successfully", true, conferenceEntity, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error creating conference: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error creating conference: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error creating conference: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update a conference
     * 
     * @param conferenceDTO the conference to be updated
     * @param id the id of the conference to be updated
     * 
     * @return ApiResponse with the result of the operation
     * 
     */
    @Override
    public ApiResponse updateConference(ConferenceDTO conferenceDTO, Integer id) {
        if (conferenceDTO == null) return new ApiResponse("Conference cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (id == null) return new ApiResponse("Conference ID cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Optional<Conference> optionalConference = this.conferenceRepo.findById(id);

            if (!optionalConference.isPresent()) return new ApiResponse("Conference with id " + id + " not found", false, null, HttpStatus.NOT_FOUND);

            Conference conferenceEntity = optionalConference.get();

            conferenceEntity.setName(conferenceDTO.getName());

            this.conferenceRepo.save(conferenceEntity);
            
            return new ApiResponse("Conference updated successfully", true, conferenceEntity, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error creating edition: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error creating edition: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error creating edition: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    @SuppressWarnings("null")
    @Override
    public ApiResponse deleteConference(Integer id) {
        if(id == null) return new ApiResponse("Conference ID cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Optional<Conference> optionalConference = this.conferenceRepo.findById(id);

            if (!optionalConference.isPresent()) return new ApiResponse("Conference with id " + id + " not found", false, null, HttpStatus.NOT_FOUND);

            Conference deletedConference = optionalConference.get();

            this.conferenceRepo.delete(deletedConference);

            return new ApiResponse("Conference deleted successfully", true, deletedConference, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error deleting conference: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error deleting conference: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error deleting conference: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ApiResponse getConferenceByID(Integer id) {
        if (id == null) return new ApiResponse("Conference ID cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Optional<Conference> optionalConference = this.conferenceRepo.findById(id);
            
            if (!optionalConference.isPresent()) return new ApiResponse("Conference with id " + id + " not found", false, null, HttpStatus.NOT_FOUND);

            Conference conference = optionalConference.get();

            return new ApiResponse("Conference retrieved successfully", true, conference, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error retrieving conference: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error retrieving conference: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error retrieving conference: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all conferences paginated
     * 
     * @param page the page number
     * @param size the size of the page
     * 
     * @return ApiResponse with the result of the operation
     * 
     */
    @Override
    @Transactional
    public ApiResponse getAllConferencesPaginated(Integer page, Integer size) {
        if (page == null) return new ApiResponse("Page cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (size == null) return new ApiResponse("Size cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {

            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Conference> conferencePage = this.conferenceRepo.findAll(pageable);

            if(conferencePage.isEmpty()) return new ApiResponse("No conferences found", true, null, HttpStatus.NOT_FOUND);

            List<Conference> conferences = conferencePage.getContent();

            return new ApiResponse("Conferences retrieved successfully", true, conferences, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error retrieving conferences: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error retrieving conferences: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error retrieving conferences: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all conferences by name containing paginated
     * 
     * @param name the name of the conference
     * @param page the page number
     * @param size the size of the page
     * 
     * @return ApiResponse with the result of the operation
     * 
     */
    @Override
    @Transactional
    public ApiResponse getConferencesByNamePaginated(String name, Integer page, Integer size) {
        if (name == null) return new ApiResponse("Name cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (page == null) return new ApiResponse("Page cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (size == null) return new ApiResponse("Size cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Conference> conferencePage = this.conferenceRepo.findByNameContaining(name, pageable);

            if(conferencePage.isEmpty()) return new ApiResponse("No conferences found", true, Collections.emptyList(), HttpStatus.NOT_FOUND);

            List<Conference> conferences = conferencePage.getContent();

            return new ApiResponse("Conferences retrieved successfully", true, conferences, HttpStatus.OK);


        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error retrieving conferences: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error retrieving conferences: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error retrieving conferences: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

}
