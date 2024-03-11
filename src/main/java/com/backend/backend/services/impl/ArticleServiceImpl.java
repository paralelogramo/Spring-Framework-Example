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
import com.backend.backend.entities.Edition;
import com.backend.backend.payloads.ArticleDTO;
import com.backend.backend.repositories.ArticleRepo;
import com.backend.backend.repositories.EditionRepo;
import com.backend.backend.services.ArticleService;
import com.backend.backend.utils.ApiResponse;
import com.backend.backend.utils.Transformations;

import jakarta.persistence.PersistenceException;

@Service
public class ArticleServiceImpl implements ArticleService{
    
    @Autowired
    private EditionRepo editionRepo;

    @Autowired
    private ArticleRepo articleRepo;

    /**
     * This method creates an article
     * 
     * @param articleDTO articleDTO object with the information of the Article to be created
     * 
     * @return ApiResponse indicating the result of the operation
     */
    @Override
    public ApiResponse createArticle(ArticleDTO articleDTO) {
        if (articleDTO == null) return new ApiResponse("Article cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            Optional<Edition> optionalEdition = this.editionRepo.findById(articleDTO.getRef_edition());

            if (!optionalEdition.isPresent()) return new ApiResponse("Edition not found", false, null, HttpStatus.NOT_FOUND);

            Article articleEntity = Transformations.dtoToArticle(articleDTO);
            Edition editionEntity = optionalEdition.get();

            articleEntity.setEdition(editionEntity);

            this.articleRepo.save(articleEntity);

            return new ApiResponse("Article created successfully", true, articleEntity, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error creating article: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error creating article: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error creating article: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Service to update an article
     * 
     * @param article ArticleDTO object with the information of the Article to be updated
     * @param id Integer with the ID of the article to be updated
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @Override
    public ApiResponse updateArticle(ArticleDTO article, Integer id) {
        if (article == null) return new ApiResponse("Article cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (id == null) return new ApiResponse("Article ID cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            
            Optional<Article> optionalArticle = this.articleRepo.findById(id);
            if (!optionalArticle.isPresent()) return new ApiResponse("Article not found", false, null, HttpStatus.NOT_FOUND);
            Article articleEntity = Transformations.dtoToArticle(article);

            Optional<Edition> optionalEdition = this.editionRepo.findById(article.getRef_edition());
            if (!optionalEdition.isPresent()) return new ApiResponse("Edition not found", false, null, HttpStatus.NOT_FOUND);
            Edition editionEntity = optionalEdition.get();

            articleEntity.setTitle(article.getTitle());
            articleEntity.setEdition(editionEntity);

            this.articleRepo.save(articleEntity);

            return new ApiResponse("Article updated successfully", true, articleEntity, HttpStatus.OK);

            
        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error updating article: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error updating article: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error updating article: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Service to get an article by ID
     * 
     * @param id Integer with the ID of the article to be retrieved
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @Override
    public ApiResponse getArticleByID(Integer id) {
        if (id == null) return new ApiResponse("Article ID cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            
            Optional<Article> optionalArticle = this.articleRepo.findById(id);

            if (!optionalArticle.isPresent()) return new ApiResponse("Article not found", false, null, HttpStatus.NOT_FOUND);

            Article articleEntity = optionalArticle.get();

            return new ApiResponse("Article retrieved successfully", true, articleEntity, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error getting article: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error getting article: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error getting article: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Service to delete an article
     * 
     * @param id Integer with the ID of the article to be deleted
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @SuppressWarnings("null")
    @Override
    public ApiResponse deleteArticle(Integer id) {
        if (id == null) return new ApiResponse("Article ID cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            
            Optional<Article> optionalArticle = this.articleRepo.findById(id);

            if (!optionalArticle.isPresent()) return new ApiResponse("Article not found", false, null, HttpStatus.NOT_FOUND);

            Article articleEntity = optionalArticle.get();

            this.articleRepo.delete(articleEntity);

            return new ApiResponse("Article deleted successfully", true, null, HttpStatus.OK);
            

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error deleting article: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error deleting article: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error deleting article: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Service to get all articles
     * 
     * @param page Integer with the page number
     * @param size Integer with the size of the page
     * 
     * @return ApiResponse object with the result of the operation
     * 
     */
    @Override
    public ApiResponse getAllArticlesPaginated(Integer page, Integer size) {
        if (page == null) return new ApiResponse("Page cannot be null", false, null, HttpStatus.BAD_REQUEST);
        if (size == null) return new ApiResponse("Size cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            
            Pageable pageable = PageRequest.of(page, size);
            Page<Article> articlePage = this.articleRepo.findAll(pageable);

            if (articlePage.isEmpty()) return new ApiResponse("No articles found", false, null, HttpStatus.NOT_FOUND);
            
            List<Article> articles = articlePage.getContent();

            return new ApiResponse("Articles retrieved successfully", true, articles , HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ApiResponse("Error getting articles: Data integrity violation", false, null, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            return new ApiResponse("Error getting articles: Persistence error", false, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse("Error getting articles: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);
        }
    }

}
