package com.backend.backend.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.payloads.AuthorDTO;
import com.backend.backend.services.AuthorService;
import com.backend.backend.utils.ApiResponse;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/author")
public class AuthorController {
    
    @Autowired
    private AuthorService authorService;

    // POST - Create a new author
    @PostMapping("/")
    public ResponseEntity<ApiResponse> createAuthor(@Valid @RequestBody AuthorDTO conferenceDto) {
        ApiResponse response = this.authorService.createAuthor(conferenceDto);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> putAuthor(@PathVariable Integer id, @Valid @RequestBody AuthorDTO conferenceDto) {
        ApiResponse response = this.authorService.updateAuthor(conferenceDto, id);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAuthor(@Valid @PathVariable("id") Integer id) {
        ApiResponse response = this.authorService.deleteAuthor(id);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    // GET
    @GetMapping("/getByID/{id}")
    public ResponseEntity<ApiResponse> getAuthor(@Valid @PathVariable("id") Integer id) {
        ApiResponse response = this.authorService.getAuthorByID(id);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    // GET ALL
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllAuthor(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size", defaultValue = "10") int size) {
        ApiResponse response = this.authorService.getAllAuthorsPaginated(page, size);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
    
}
