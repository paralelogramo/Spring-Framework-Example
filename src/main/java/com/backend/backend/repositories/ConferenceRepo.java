package com.backend.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend.entities.Conference;

@Repository
public interface ConferenceRepo extends JpaRepository<Conference, Integer>{

    Page<Conference> findByName(String name, Pageable pageable); 

    Page<Conference> findByNameContaining(String name, Pageable pageable);

}
