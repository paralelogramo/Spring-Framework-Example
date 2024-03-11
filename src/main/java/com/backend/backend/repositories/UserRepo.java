package com.backend.backend.repositories;

import com.backend.backend.entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepo extends JpaRepository<User, String>{
    
    Optional<UserDetails> findByUsername(String email);
}
