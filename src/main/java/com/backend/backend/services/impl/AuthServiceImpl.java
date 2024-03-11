package com.backend.backend.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.backend.entities.User;
import com.backend.backend.jwt.JwtService;
import com.backend.backend.payloads.UserDTO;
import com.backend.backend.repositories.UserRepo;
import com.backend.backend.services.AuthService;
import com.backend.backend.utils.ApiResponse;
import com.backend.backend.utils.AuthResponse;
import com.backend.backend.utils.Role;


@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * This method logs in a user
     * 
     * @param email email of the user
     * @param password password of the user
     * 
     * @return ApiResponse indicating the result of the operation
     * 
     */
    @Override
    public ApiResponse login(UserDTO userDTO) {
        if (userDTO == null) return new ApiResponse("User cannot be null", false, null, HttpStatus.BAD_REQUEST);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        Optional<UserDetails> optionalUser = userRepo.findByUsername(userDTO.getEmail());

        if (!optionalUser.isPresent()) {
            return new ApiResponse("User doesn't exist", false, null, HttpStatus.NOT_FOUND);
        }

        UserDetails user = optionalUser.get();

        String token = jwtService.getToken(user);

        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .build();

        return new ApiResponse("User logged in successfully", true, authResponse, HttpStatus.OK);
    }

    /**
     * This method registers a new user
     * 
     * @param email email of the user
     * @param password password of the user
     * 
     * @return ApiResponse indicating the result of the operation
     * 
     */
    @SuppressWarnings("null")
    @Override
    public ApiResponse register(UserDTO userDTO) {
        if (userDTO == null) return new ApiResponse("User cannot be null", false, null, HttpStatus.BAD_REQUEST);

        try {
            User user = User.builder()
                    .username(userDTO.getEmail())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .role(Role.USER)
                    .build();
            
            this.userRepo.save(user);

            AuthResponse authResponse = AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .build();

            return new ApiResponse("User registered successfully", true, authResponse, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ApiResponse("Error registering user: " + e.getMessage(), false, null, HttpStatus.BAD_REQUEST);            
        }
    }

    @Override
    public ApiResponse logout(String email) {
        return null;
    }
    
}