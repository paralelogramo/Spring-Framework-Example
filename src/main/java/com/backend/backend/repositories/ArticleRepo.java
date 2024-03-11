package com.backend.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend.entities.Article;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Integer>{
    
}
