package com.backend.backend.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.payloads.EditionDTO;
import com.backend.backend.services.EditionService;
import com.backend.backend.utils.ApiResponse;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/edition")
public class EditionController {

    @Autowired
    private EditionService editionService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createEdition(@Valid @RequestBody EditionDTO editionDto) {
        ApiResponse response = this.editionService.createEdition(editionDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> putEdition(@PathVariable("id") Integer id, @Valid @RequestBody EditionDTO editionDto) {
        ApiResponse response = this.editionService.updateEdition(id, editionDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteEdition(@PathVariable("id") Integer id){
        ApiResponse response = this.editionService.deleteEdition(id);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);        
    }

    @GetMapping("/getByID/{id}")
    public ResponseEntity<ApiResponse> getEditionById(@PathVariable("id") Integer id){
        ApiResponse response = this.editionService.getEditionByID(id);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllEditions(@RequestParam(name = "page", defaultValue = "1", required = false) Integer page, @RequestParam(name = "size", defaultValue = "10", required = false) Integer size){
        ApiResponse response = this.editionService.getAllEditionsPaginated(page, size);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/getByYear/{year}")
    public ResponseEntity<ApiResponse> getEditionByYear(@PathVariable("year") Integer year, @RequestParam(name = "page", defaultValue = "1", required = false) Integer page, @RequestParam(name = "size", defaultValue = "10", required = false) Integer size){
        ApiResponse response = this.editionService.getEditionsByYearPaginated(year, page, size);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/getBetweenYears/{startYear}/{endYear}")
    public ResponseEntity<ApiResponse> getEditionBetweenYears(@PathVariable("startYear") Integer startYear, @PathVariable("endYear") Integer endYear, @RequestParam(name = "page", defaultValue = "1", required = false) Integer page, @RequestParam(name = "size", defaultValue = "10", required = false) Integer size){
        ApiResponse response = this.editionService.getEditionsBetweenYearsPaginated(startYear, endYear, page, size);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
    
    @GetMapping("/getByCity/{city}")
    public ResponseEntity<ApiResponse> getEditionByCity(@PathVariable("city") String city, @RequestParam(name = "page", defaultValue = "1", required = false) Integer page, @RequestParam(name = "size", defaultValue = "10", required = false) Integer size){
        ApiResponse response = this.editionService.getEditionsByCityPaginated(city, page, size);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/getByDate/{date}")
    public ResponseEntity<ApiResponse> getEditionByDate(@PathVariable("date") String date, @RequestParam(name = "page", defaultValue = "1", required = false) Integer page, @RequestParam(name = "size", defaultValue = "10", required = false) Integer size){
        ApiResponse response = this.editionService.getEditionsByDatePaginated(date, page, size);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

}
