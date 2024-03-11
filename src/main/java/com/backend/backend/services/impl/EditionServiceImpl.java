package com.backend.backend.services.impl;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.backend.backend.entities.Conference;
import com.backend.backend.entities.Edition;
import com.backend.backend.payloads.EditionDTO;
import com.backend.backend.repositories.ConferenceRepo;
import com.backend.backend.repositories.EditionRepo;
import com.backend.backend.services.EditionService;
import com.backend.backend.utils.ApiResponse;
import com.backend.backend.utils.Transformations;

import jakarta.persistence.PersistenceException;

@Service
public class EditionServiceImpl implements EditionService{

    @Autowired
    private ConferenceRepo conferenceRepo;

    @Autowired
    private EditionRepo editionRepo;
    
    /**
     * Create a new edition
     * 
     * @param editionDTO editionDTO object with the information of the Edition to be created
     * 
     * @return ApiResponse indicating the result of the operation.
     * 
     */
    @Override
    public ApiResponse createEdition(EditionDTO editionDTO) {
        if (editionDTO == null) return new ApiResponse("Edition cannot be null", false, null, HttpStatus.BAD_REQUEST);
        
        try {

            Optional<Conference> optionalConference = this.conferenceRepo.findById(editionDTO.getRef_conference());
            if (!optionalConference.isPresent()) return new ApiResponse("Conference with id " + editionDTO.getRef_conference() + " does not exist", false, null, HttpStatus.NOT_FOUND);
            Conference conferenceEntity = optionalConference.get();            

            Edition editionEntity = Transformations.dtoToEdition(editionDTO);
            editionEntity.setConference(conferenceEntity);

            this.editionRepo.save(editionEntity);

            return new ApiResponse("Edition created successfully", true, editionEntity, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error creating edition: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error creating edition: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error creating edition: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update an existing edition
     * 
     * @param id         The id of the edition to be updated
     * @param editionDTO editionDTO object with the information of the Edition to be created
     * 
     * @return ApiResponse indicating the result of the operation
     * 
     */
    @Override
    public ApiResponse updateEdition(Integer id, EditionDTO editionDTO) {
        if (id == null ) return new ApiResponse("Edition id cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (editionDTO == null) return new ApiResponse("Edition cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {

            Optional<Edition> optionalEdition = this.editionRepo.findById(id);
            if (!optionalEdition.isPresent()) return new ApiResponse("Edition with id " + id + " does not exist", false, null, HttpStatus.NOT_FOUND);
            Edition editionEntity = optionalEdition.get();

            Optional<Conference> optionalConference = this.conferenceRepo.findById(editionDTO.getRef_conference());
            if (!optionalConference.isPresent()) return new ApiResponse("Conference with id " + editionDTO.getRef_conference() + " does not exist", false, null, HttpStatus.NOT_FOUND);
            Conference conferenceEntity = optionalConference.get();

            editionEntity.setCity(editionDTO.getCity());
            editionEntity.setDate(editionDTO.getDate());
            editionEntity.setYear(editionDTO.getYear());
            editionEntity.setConference(conferenceEntity);

            this.editionRepo.save(editionEntity);

            return new ApiResponse("Edition updated successfully", true, editionEntity, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error updating edition: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error updating edition: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error updating edition: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get an edition by id
     * 
     * @param id The id of the edition to be retrieved
     * 
     * @return ApiResponse indicating the result of the operation
     * 
     */
    @Override
    public ApiResponse getEditionByID(Integer id) {
        if (id == null) return new ApiResponse("Edition id cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Optional<Edition> optionalEdition = this.editionRepo.findById(id);
            if (!optionalEdition.isPresent()) return new ApiResponse("Edition with id " + id + " does not exist", false, null, HttpStatus.NOT_FOUND);
            Edition editionEntity = optionalEdition.get();

            return new ApiResponse("Edition retrieved successfully", true, editionEntity, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error getting edition: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error getting edition: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error getting edition: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all editions
     * 
     * @param page The page number
     * @param size The size of the page
     * 
     * @return ApiResponse indicating the result of the operation
     */
    @Override
    public ApiResponse getAllEditionsPaginated(Integer page, Integer size) {
        if (page == null) return new ApiResponse("Page cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (size == null) return new ApiResponse("Size cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {

            Pageable pageable = PageRequest.of(page, size);
            Page<Edition> editionsPage = this.editionRepo.findAll(pageable);

            if (editionsPage.isEmpty()) return new ApiResponse("No editions found", true, Collections.emptyList(), HttpStatus.NOT_FOUND);
            List<Edition> editions = editionsPage.getContent();
            
            return new ApiResponse("Editions retrieved successfully", true, editions, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error getting editions: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error getting editions: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error getting editions: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
        
    }

    /**
     * Delete an edition by id
     * 
     * @param id The id of the edition to be deleted
     * 
     * @return ApiResponse indicating the result of the operation
     */
    @SuppressWarnings("null")
    @Override
    public ApiResponse deleteEdition(Integer id) {
        if(id == null) return new ApiResponse("Edition id cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {

            Optional<Edition> optionalEdition = this.editionRepo.findById(id);
            if (!optionalEdition.isPresent()) return new ApiResponse("Edition with id " + id + " does not exist", false, null, HttpStatus.NOT_FOUND);
            Edition editionEntity = optionalEdition.get();

            this.editionRepo.delete(editionEntity);

            return new ApiResponse("Edition deleted successfully", true, editionEntity, HttpStatus.OK);
            
        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error deleting edition: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error deleting edition: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error deleting edition: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
        
    }

    /**
     * Get all editions by year
     * 
     * @param year The year of the editions to be retrieved
     * @param page The page number
     * @param size The size of the page
     * 
     * @return ApiResponse indicating the result of the operation
     * 
     */
    @Override
    public ApiResponse getEditionsByYearPaginated(Integer year, Integer page, Integer size) {
        if(year == null) return new ApiResponse("Year cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if(page == null) return new ApiResponse("Page cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if(size == null) return new ApiResponse("Size cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
                
            Pageable pageable = PageRequest.of(page, size);
            Page<Edition> editionPage = this.editionRepo.findByYear(year, pageable);

            if (editionPage.isEmpty()) return new ApiResponse("No editions found", true, Collections.emptyList(), HttpStatus.NOT_FOUND);

            List<Edition> editions = editionPage.getContent();
            

            return new ApiResponse("Editions retrieved successfully", true, editions, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error getting editions: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error getting editions: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error getting editions: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
        
    }

    /**
     * Get all editions between years
     * 
     * @param startYear The start year of the editions to be retrieved
     * @param endYear The end year of the editions to be retrieved
     * @param page The page number
     * @param size The size of the page
     * 
     * @return ApiResponse indicating the result of the operation
     */
    @Override
    public ApiResponse getEditionsBetweenYearsPaginated(Integer startYear, Integer endYear, Integer page, Integer size) {
        if(startYear == null) return new ApiResponse("Start year cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if(endYear == null) return new ApiResponse("End year cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if(page == null) return new ApiResponse("Page cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if(size == null) return new ApiResponse("Size cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Edition> editionPage = this.editionRepo.findByYearBetween(startYear, endYear, pageable);

            if (editionPage.isEmpty()) return new ApiResponse("No editions found", true, Collections.emptyList(), HttpStatus.NOT_FOUND);

            List<Edition> editions = editionPage.getContent();

            return new ApiResponse("Editions retrieved successfully", true, editions, HttpStatus.OK);
    
        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error getting editions: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error getting editions: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error getting editions: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
        
    }

    /**
     * Get all editions by city
     * 
     * @param city The city of the editions to be retrieved
     * @param page The page number
     * @param size The size of the page
     * 
     * @return ApiResponse indicating the result of the operation
     * 
     */
    @Override
    public ApiResponse getEditionsByCityPaginated(String city, Integer page, Integer size) {
        if(city == null) return new ApiResponse("City cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if(page == null) return new ApiResponse("Page cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if(size == null) return new ApiResponse("Size cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Edition> editionPage = this.editionRepo.findByCityContaining(city, pageable);

            if (editionPage.isEmpty()) return new ApiResponse("No editions found", true, Collections.emptyList(), HttpStatus.NOT_FOUND);

            List<Edition> editions = editionPage.getContent();

            return new ApiResponse("Editions retrieved successfully", true, editions, HttpStatus.OK);
    
        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error getting editions: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error getting editions: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error getting editions: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all editions by date
     * 
     * @param date The date of the editions to be retrieved
     * @param page The page number
     * @param size The size of the page
     * 
     * @return ApiResponse indicating the result of the operation
     * 
     */
    @Override
    public ApiResponse getEditionsByDatePaginated(String date, Integer page, Integer size) {
        if(date == null) return new ApiResponse("Date cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if(page == null) return new ApiResponse("Page cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if(size == null) return new ApiResponse("Size cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            try {
                Pageable pageable = PageRequest.of(page, size);
                Page<Edition> editionPage = this.editionRepo.findByDate(d, pageable);

                if (editionPage.isEmpty()) return new ApiResponse("No editions found", true, Collections.emptyList(), HttpStatus.NOT_FOUND);

                List<Edition> editions = editionPage.getContent();

                return new ApiResponse("Editions retrieved successfully", true, editions, HttpStatus.OK);

            } catch (DataIntegrityViolationException e) {
                return new ApiResponse("Error getting editions: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
            } catch (PersistenceException e) {
                return new ApiResponse("Error getting editions: Persistence error", false, null, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ApiResponse("Error getting editions: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ApiResponse("Error parsing date: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }

    }

}
