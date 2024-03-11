package com.backend.backend.payloads;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EditionDTO {
    
    @JsonIgnore
    private int id;
    
    @NotNull(message = "Id of conference is mandatory")
    private int ref_conference;
    
    @NotNull(message = "Year is mandatory")
    private int year;
    
    @NotNull(message = "Date is mandatory")
    private Date date;
    
    @NotBlank(message = "City is mandatory")
    @NotNull(message = "City is mandatory")
    @Size(min = 3, max = 64, message = "City must be between 3 and 64 characters")
    private String city;
}
