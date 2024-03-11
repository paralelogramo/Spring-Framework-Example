package com.backend.backend.services;

import com.backend.backend.payloads.ArticleDTO;
import com.backend.backend.utils.ApiResponse;

public interface ArticleService {
    
    ApiResponse createArticle (ArticleDTO article);

    ApiResponse updateArticle (ArticleDTO article, Integer id);

    ApiResponse getArticleByID (Integer id);

    ApiResponse getAllArticlesPaginated (Integer page, Integer size);

    ApiResponse deleteArticle (Integer id);
    
}
