package com.backend.backend.services;

import com.backend.backend.payloads.UserDTO;
import com.backend.backend.utils.ApiResponse;

public interface AuthService {
    
    ApiResponse login(UserDTO user);
    ApiResponse register(UserDTO user);
    ApiResponse logout(String email);

}
