package com.backend.backend.repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend.entities.Conference;
import com.backend.backend.entities.Edition;

@Repository
public interface EditionRepo extends JpaRepository<Edition, Integer>{

    Page<Edition> findByYear(Integer year, Pageable pageable);

    Page<Edition> findByYearBetween(Integer startYear, Integer endYear, Pageable pageable);

    Page<Edition> findByCityContaining(String city, Pageable pageable);

    Page<Edition> findByDate(Date date, Pageable pageable);

    Page<Edition> findByConference(Conference refConference, Pageable pageable);

}
