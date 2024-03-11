package com.backend.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.payloads.UserDTO;
import com.backend.backend.services.AuthService;
import com.backend.backend.utils.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody UserDTO user){
        ApiResponse response = this.authService.login(user);
        return new ResponseEntity<ApiResponse>(response, response.getStatus());
        
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserDTO user){
        ApiResponse response = this.authService.register(user);
        return new ResponseEntity<ApiResponse>(response, response.getStatus());
        
    }
}
