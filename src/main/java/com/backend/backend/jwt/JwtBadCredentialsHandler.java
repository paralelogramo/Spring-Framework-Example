package com.backend.backend.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.backend.backend.utils.ApiResponse;

@ControllerAdvice
public class JwtBadCredentialsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        ApiResponse apiResponse = new ApiResponse("Invalid login credentials", false, null, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }
}