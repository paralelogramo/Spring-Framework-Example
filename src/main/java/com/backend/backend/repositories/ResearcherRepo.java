package com.backend.backend.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend.entities.Researcher;

/*
 * defines a repository for managing Researcher entities in the backend.
 * It extends JpaRepository and is annotated with @Repository to indicate
 * it as a Spring repository component. The repository provides methods
 * for querying researchers based on various criteria such as name, surname,
 * second surname, and university, using the findByNameContaining,
 * findBySurnameContaining, findBySecSurnameContaining, and
 * findByUniversityContaining methods respectively. These methods return
 * lists of researchers matching the provided keyword.
 */

@Repository
public interface ResearcherRepo extends JpaRepository<Researcher, Integer>{

    Page<Researcher> findByNameContaining(String name, Pageable pageable);

    Page<Researcher> findBySurnameContaining(String surname, Pageable pageable);

    Page<Researcher> findBySecSurnameContaining(String secSurname, Pageable pageable);

    Page<Researcher> findByUniversityContaining(String university, Pageable pageable);

    Optional<Researcher> findByNameAndSurnameAndSecSurname(String name, String surname, String secSurname);
}