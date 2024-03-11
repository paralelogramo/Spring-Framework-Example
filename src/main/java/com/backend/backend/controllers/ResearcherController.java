package com.backend.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.payloads.ResearcherDTO;
import com.backend.backend.services.ResearcherService;
import com.backend.backend.utils.ApiResponse;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/researcher")
public class ResearcherController {
    
    @Autowired
    private ResearcherService researcherService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createResearcher(@Valid @RequestBody ResearcherDTO researcherDto) {
        ApiResponse response = this.researcherService.createResearcher(researcherDto);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> putResearcher(@PathVariable("id") Integer id, @Valid @RequestBody ResearcherDTO researcherDto) {
        ApiResponse response = this.researcherService.updateResearcher(id, researcherDto);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteResearcher(@PathVariable("id") Integer id) {
        ApiResponse response = this.researcherService.deleteResearcher(id);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/getByID/{id}")
    public ResponseEntity<ApiResponse> getResearcher(@PathVariable("id") Integer id) {
        ApiResponse response = this.researcherService.getResearcherByID(id);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllResearcherPaginated(@RequestParam(name = "page", defaultValue = "1", required = false) Integer page, @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        ApiResponse response = this.researcherService.getAllResearchersPaginated(page, size);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
    
    @GetMapping("/getByName/{name}")
    public ResponseEntity<ApiResponse> getResearcherByName(@PathVariable("name") String name, @RequestParam(name = "page", defaultValue = "1", required = false) Integer page, @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        ApiResponse response = this.researcherService.getResearchersByNamePaginated(name, page, size);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/getByUniversity/{university}")
    public ResponseEntity<ApiResponse> getResearcherByUniversity(@PathVariable("university") String university, @RequestParam(name = "page", defaultValue = "1", required = false) Integer page, @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        ApiResponse response = this.researcherService.getResearchersByUniversityPaginated(university, page, size);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/getBySurname/{surname}")
    public ResponseEntity<ApiResponse> getResearcherBySurname(@PathVariable("surname") String surname, @RequestParam(name = "page", defaultValue = "1", required = false) Integer page, @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        ApiResponse response = this.researcherService.getResearchersBySurnamePaginated(surname, page, size);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/getBySecSurname/{secSurname}")
    public ResponseEntity<ApiResponse> getResearcherBySecSurname(@PathVariable("secSurname") String secSurname, @RequestParam(name = "page", defaultValue = "1", required = false) Integer page, @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        ApiResponse response = this.researcherService.getResearchersBySecSurnamePaginated(secSurname, page, size);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
    
    
    @GetMapping("/getAllArticlesFromResearcherCompleteName/{name}/{surname}/{secSurname}")
    public ResponseEntity<ApiResponse> getArticlesFromResearcherByCompleteName(@PathVariable("name") String name, @PathVariable("surname") String surname, @PathVariable("secSurname") String secSurname){
        ApiResponse response = this.researcherService.getArticlesFromResearcherByCompleteName(name, surname, secSurname);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
}
