package com.backend.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend.entities.Author;
import com.backend.backend.entities.Researcher;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Integer>{
    
    List<Author> findByResearcher(Researcher researcher);
}
