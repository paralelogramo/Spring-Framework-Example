package com.backend.backend.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.payloads.ConferenceDTO;
import com.backend.backend.services.ConferenceService;
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

/**
 * REST Controller for managing conference-related operations.
 */
@RestController
@RequestMapping("/api/conference")
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createConference(@Valid @RequestBody ConferenceDTO conferenceDto) {
        ApiResponse response = this.conferenceService.createConference(conferenceDto);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> putConference(@PathVariable Integer id, @Valid @RequestBody ConferenceDTO conferenceDto) {
        ApiResponse response = this.conferenceService.updateConference(conferenceDto, id);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteConference(@Valid @PathVariable("id") Integer id) {
        ApiResponse response = this.conferenceService.deleteConference(id);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/getByID/{id}")
    public ResponseEntity<ApiResponse> getConference(@Valid @PathVariable("id") Integer id) {
        ApiResponse response = this.conferenceService.getConferenceByID(id);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllConferencePaginated(@RequestParam(name = "page", defaultValue = "1", required = false) int page, @RequestParam(name = "", defaultValue = "10", required = false) int size) {
        ApiResponse response = this.conferenceService.getAllConferencesPaginated(page, size);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<ApiResponse> getConferencesByName(@Valid @PathVariable("name") String name, @RequestParam(name = "page", defaultValue = "1", required = false) int page, @RequestParam(name = "size", defaultValue = "10", required = false) int size){
        ApiResponse response = this.conferenceService.getConferencesByNamePaginated(name, page, size);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
}
