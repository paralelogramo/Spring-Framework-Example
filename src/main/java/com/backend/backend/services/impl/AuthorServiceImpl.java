package com.backend.backend.services.impl;

import java.util.List;
import java.util.Optional;

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
import com.backend.backend.payloads.AuthorDTO;
import com.backend.backend.repositories.ArticleRepo;
import com.backend.backend.repositories.AuthorRepo;
import com.backend.backend.repositories.ResearcherRepo;
import com.backend.backend.services.AuthorService;
import com.backend.backend.utils.ApiResponse;
import com.backend.backend.utils.Transformations;

import jakarta.persistence.PersistenceException;

@Service
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    private ArticleRepo articleRepo;

    @Autowired
    private ResearcherRepo researcherRepo;

    @Autowired
    private AuthorRepo authorRepo;

    /**
     * Service to create an author
     * 
     * @param authorDTO authorDTO object with the information of the Edition to be created
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @Override
    public ApiResponse createAuthor(AuthorDTO authorDTO) {
        if (authorDTO == null) return new ApiResponse("Author cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {

            Optional<Article> optionalArticle = this.articleRepo.findById(authorDTO.getRef_article());
            if (!optionalArticle.isPresent()) return new ApiResponse("Article not found", false, null, HttpStatus.NOT_FOUND);
            Article articleEntity = optionalArticle.get();

            Optional<Researcher> optionalResearcher = this.researcherRepo.findById(authorDTO.getRef_researcher());
            if (!optionalResearcher.isPresent()) return new ApiResponse("Researcher not found", false, null, HttpStatus.NOT_FOUND);
            Researcher researcherEntity = optionalResearcher.get();

            Author authorEntity = Transformations.dtoToAuthor(authorDTO);
            authorEntity.setArticle(articleEntity);
            authorEntity.setResearcher(researcherEntity);

            this.authorRepo.save(authorEntity);

            return new ApiResponse("Author created successfully", true, authorEntity, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error creating author: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error creating author: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error creating author: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
        
    }

    /**
     * Service to update an author
     * 
     * @param author authorDTO object with the information of the Edition to be updated
     * @param id Integer with the ID of the author to be updated
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @Override
    public ApiResponse updateAuthor(AuthorDTO author, Integer id) {
        if (author == null) return new ApiResponse("Author cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (id == null) return new ApiResponse("Author ID cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            
            Optional<Author> optionalAuthor = this.authorRepo.findById(id);
            if (!optionalAuthor.isPresent()) return new ApiResponse("Author not found", false, null, HttpStatus.NOT_FOUND);
            Author authorEntity = optionalAuthor.get();

            Optional<Article> optionalArticle = this.articleRepo.findById(author.getRef_article());
            if (!optionalArticle.isPresent()) return new ApiResponse("Article not found", false, null, HttpStatus.NOT_FOUND);
            Article articleEntity = optionalArticle.get();

            Optional<Researcher> optionalResearcher = this.researcherRepo.findById(author.getRef_researcher());
            if (!optionalResearcher.isPresent()) return new ApiResponse("Researcher not found", false, null, HttpStatus.NOT_FOUND);
            Researcher researcherEntity = optionalResearcher.get();

            authorEntity.setArticle(articleEntity);
            authorEntity.setResearcher(researcherEntity);

            this.authorRepo.save(authorEntity);

            return new ApiResponse("Author updated successfully", true, authorEntity, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error updating author: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error updating author: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error updating author: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
        
    }

    /**
     * Service to get an author by ID
     * 
     * @param id Integer with the ID of the author to be retrieved
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @Override
    public ApiResponse getAuthorByID(Integer id) {
        if (id == null) return new ApiResponse("Author ID cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Optional<Author> optionalAuthor = this.authorRepo.findById(id);
            if (!optionalAuthor.isPresent()) return new ApiResponse("Author not found", false, null, HttpStatus.NOT_FOUND);
            Author authorEntity = optionalAuthor.get();

            return new ApiResponse("Author retrieved successfully", true, authorEntity, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error getting author: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error getting author: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error getting author: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
        
    }

    /**
     * Service to get all authors
     * 
     * @param page Integer with the page number of the authors to be retrieved
     * @param size Integer with the size of the page of authors to be retrieved
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @Override
    public ApiResponse getAllAuthorsPaginated(Integer page, Integer size) {
        if (page == null) return new ApiResponse("Page cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (size == null) return new ApiResponse("Size cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            
            Pageable pageable = PageRequest.of(page, size);
            Page<Author> authorsPage = this.authorRepo.findAll(pageable);

            if (authorsPage.isEmpty()) return new ApiResponse("Authors not found", false, null, HttpStatus.NOT_FOUND);

            List<Author> authors = authorsPage.getContent();

            return new ApiResponse("Authors retrieved successfully", true, authors, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error getting authors: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error getting authors: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error getting authors: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
        
    }

    /**
     * Service to get all authors
     * 
     * @param page Integer with the page number of the authors to be retrieved
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @SuppressWarnings("null")
    @Override
    public ApiResponse deleteAuthor(Integer id) {
        if (id == null) return new ApiResponse("Author ID cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            
            Optional<Author> optionalAuthor = this.authorRepo.findById(id);

            if (!optionalAuthor.isPresent()) return new ApiResponse("Author not found", false, null, HttpStatus.NOT_FOUND);

            Author authorEntity = optionalAuthor.get();

            this.authorRepo.delete(authorEntity);

            return new ApiResponse("Author deleted successfully", true, null, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error deleting author: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error deleting author: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error deleting author: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
        
    }

}