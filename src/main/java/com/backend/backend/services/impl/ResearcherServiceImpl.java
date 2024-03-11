package com.backend.backend.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.backend.backend.entities.Article;
import com.backend.backend.entities.Author;
import com.backend.backend.entities.Researcher;
import com.backend.backend.payloads.ResearcherDTO;
import com.backend.backend.repositories.AuthorRepo;
import com.backend.backend.repositories.ResearcherRepo;
import com.backend.backend.services.ResearcherService;
import com.backend.backend.utils.ApiResponse;
import com.backend.backend.utils.Transformations;

import jakarta.persistence.PersistenceException;

@Service
public class ResearcherServiceImpl implements ResearcherService{
    
    @Autowired
    private ResearcherRepo researcherRepo;

    @Autowired
    private AuthorRepo authorRepo;

    /**
     * Method to create a new Researcher
     * 
     * @param researcherDTO ResearcherDTO object with the information of the Researcher to be created
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @SuppressWarnings("null")
    @Override
    public ApiResponse createResearcher(ResearcherDTO researcherDTO) {
        if (researcherDTO == null) return new ApiResponse("Researcher cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Researcher researcherEntity = Transformations.dtoToResearcher(researcherDTO);

            this.researcherRepo.save(researcherEntity);

            return new ApiResponse("Researcher created successfully", true, researcherEntity, HttpStatus.CREATED);


        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error creating researcher: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error creating researcher: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error creating researcher: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to update a Researcher
     * 
     * @param id Integer with the ID of the Researcher to be updated
     * @param researcher ResearcherDTO object with the information of the Researcher to be updated
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @Override
    public ApiResponse updateResearcher(Integer id, ResearcherDTO researcher) {
        if (researcher == null) return new ApiResponse("Researcher cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (id == null) return new ApiResponse("ID cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Optional<Researcher> optionalResearcher = this.researcherRepo.findById(id);

            if (!optionalResearcher.isPresent()) return new ApiResponse("Researcher not found", false, null, HttpStatus.NOT_FOUND);

            Researcher researcherEntity = optionalResearcher.get();

            researcherEntity.setName(researcher.getName());
            researcherEntity.setSurname(researcher.getSurname());
            researcherEntity.setSecSurname(researcher.getSecSurname());
            researcherEntity.setUniversity(researcher.getUniversity());

            this.researcherRepo.save(researcherEntity);

            return new ApiResponse("Researcher updated successfully", true, researcherEntity, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error updating researcher: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error updating researcher: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error updating researcher: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
        
    }

    /**
     * Method to get a Researcher by its ID
     * 
     * @param id Integer with the ID of the Researcher to be retrieved
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @Override
    public ApiResponse getResearcherByID(Integer id) {
        if (id == null) return new ApiResponse("ID cannot be null", false, null, HttpStatus.BAD_REQUEST);
        
        try {
            Optional<Researcher> optionalResearcher = this.researcherRepo.findById(id);
            if (!optionalResearcher.isPresent()) return new ApiResponse("Researcher not found", false, null, HttpStatus.BAD_REQUEST);

            Researcher researcher = optionalResearcher.get();

            return new ApiResponse("Researcher found", true, researcher, HttpStatus.OK);
            
        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error retrieving researcher: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error retrieving researcher: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error retrieving researcher: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to delete a Researcher by its ID
     * 
     * @param id Integer with the ID of the Researcher to be deleteddwsadwsa
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @SuppressWarnings("null")
    @Override
    public ApiResponse deleteResearcher(Integer id) {
        if (id == null) return new ApiResponse("ID cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Optional<Researcher> optionalResearcher = this.researcherRepo.findById(id);

            if (!optionalResearcher.isPresent()) return new ApiResponse("Researcher not found", false, null, HttpStatus.BAD_REQUEST);

            Researcher researcherEntity = optionalResearcher.get();

            this.researcherRepo.delete(researcherEntity);

            return new ApiResponse("Researcher deleted successfully", true, null, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error deleting researcher: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error deleting researcher: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error deleting researcher: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to get all Researchers
     * 
     * @param page Integer with the page number
     * @param size Integer with the size of the page
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @Override
    public ApiResponse getAllResearchersPaginated(Integer page, Integer size) {
        if (page == null) return new ApiResponse("Page cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (size == null) return new ApiResponse("Size cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Researcher> researcherPage = this.researcherRepo.findAll(pageable);

            if (researcherPage.isEmpty()) return new ApiResponse("No researchers found", true, null, HttpStatus.NOT_FOUND);

            List<Researcher> researchers = researcherPage.getContent();

            return new ApiResponse("Researchers retrieved successfully", true, researchers, HttpStatus.OK);
            
        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error retrieving researchers: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error retrieving researchers: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error retrieving researchers: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to get Researchers by name
     * 
     * @param name String with the name of the Researcher
     * @param page Integer with the page number
     * @param size Integer with the size of the page
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @Override
    public ApiResponse getResearchersByNamePaginated(String name, Integer page, Integer size) {
        if (name == null) return new ApiResponse("Name cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (page == null) return new ApiResponse("Page cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (size == null) return new ApiResponse("Size cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Researcher> researcherPage = this.researcherRepo.findByNameContaining(name, pageable);
            
            if (researcherPage.isEmpty()) return new ApiResponse("No researchers found", true, null, HttpStatus.NOT_FOUND);

            List<Researcher> researchers = researcherPage.getContent();

            return new ApiResponse("Researchers retrieved successfully", true, researchers, HttpStatus.OK);
            
        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error retrieving researcher: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error retrieving researcher: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error retrieving researcher: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to get Researchers by surname
     * 
     * @param surname String with the surname of the Researcher
     * @param page Integer with the page number
     * @param size Integer with the size of the page
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @Override
    public ApiResponse getResearchersBySurnamePaginated(String surname, Integer page, Integer size) {
        if (surname == null) return new ApiResponse("Surname cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (page == null) return new ApiResponse("Page cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (size == null) return new ApiResponse("Size cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Researcher> researcherPage = this.researcherRepo.findBySurnameContaining(surname, pageable);
            
            if (researcherPage.isEmpty()) return new ApiResponse("No researchers found", true, null, HttpStatus.NOT_FOUND);

            List<Researcher> researchers = researcherPage.getContent();

            return new ApiResponse("Researchers retrieved successfully", true, researchers, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error creating researcher: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error creating researcher: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error creating researcher: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to get Researchers by second surname
     * 
     * @param secSurname String with the second surname of the Researcher
     * @param page Integer with the page number
     * @param size Integer with the size of the page
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @Override
    public ApiResponse getResearchersBySecSurnamePaginated(String secSurname, Integer page, Integer size) {
        if (secSurname == null) return new ApiResponse("Second surname cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (page == null) return new ApiResponse("Page cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (size == null) return new ApiResponse("Size cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Researcher> researcherPage = this.researcherRepo.findBySecSurnameContaining(secSurname, pageable);

            if (researcherPage.isEmpty()) return new ApiResponse("No researchers found", true, null, HttpStatus.NOT_FOUND);

            List<Researcher> researchers = researcherPage.getContent();

            return new ApiResponse("Researchers retrieved successfully", true, researchers, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error creating researcher: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error creating researcher: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error creating researcher: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to get Researchers by university
     * 
     * @param university String with the university of the Researcher
     * @param page Integer with the page number
     * @param size Integer with the size of the page
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @Override
    public ApiResponse getResearchersByUniversityPaginated(String university, Integer page, Integer size) {
        if (university == null) return new ApiResponse("University cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (page == null) return new ApiResponse("Page cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (size == null) return new ApiResponse("Size cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Researcher> researcherPage = this.researcherRepo.findByUniversityContaining(university, pageable);
            
            if (researcherPage.isEmpty()) return new ApiResponse("No researchers found", true, null, HttpStatus.NOT_FOUND);

            List<Researcher> researchers = researcherPage.getContent();

            return new ApiResponse("Researchers retrieved successfully", true, researchers, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error creating researcher: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error creating researcher: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error creating researcher: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Method to get all articles from a researcher by its complete name
     * 
     * @param name String with the name of the Researcher
     * @param surname String with the surname of the Researcher
     * @param secSurname String with the second surname of the Researcher
     */
    @Override
    public ApiResponse getArticlesFromResearcherByCompleteName(String name, String surname, String secSurname) {
        if (name == null) return new ApiResponse("Name cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (surname == null) return new ApiResponse("Surname cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (secSurname == null) return new ApiResponse("Second surname cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {

            Optional<Researcher> optionalResearcher = null;

            try {
                optionalResearcher = this.researcherRepo.findByNameAndSurnameAndSecSurname(name, surname, secSurname);
            } catch (DataIntegrityViolationException e) {
                return new ApiResponse("Error creating researcher: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
            } catch (PersistenceException e) {
                return new ApiResponse("Error creating researcher: Persistence error", false, null, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ApiResponse("Error creating researcher: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
            }

            Researcher researcher = optionalResearcher.get();
            List<Author> authors = null;

            try {
                authors = this.authorRepo.findByResearcher(researcher);   
            } catch (DataIntegrityViolationException e) {
                return new ApiResponse("Error creating researcher: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
            } catch (PersistenceException e) {
                return new ApiResponse("Error creating researcher: Persistence error", false, null, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ApiResponse("Error creating researcher: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
            }
            
            List<Article> articles = authors.stream().map(a -> a.getArticle()).collect(Collectors.toList());

            if (articles.isEmpty()) {
                return new ApiResponse("No articles found", true, null, HttpStatus.NOT_FOUND);
            }

            return new ApiResponse("Articles retrieved successfully", true, articles, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error creating researcher: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error creating researcher: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error creating researcher: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }
}
