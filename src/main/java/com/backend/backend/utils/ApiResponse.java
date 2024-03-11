package com.backend.backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApiResponse {
    
    private String message;
    private Boolean success;
    private Object data;

    @NonNull
    private HttpStatus status;

}
