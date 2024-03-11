package com.backend.backend.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Used to represent researcher data in a Data Transfer Object (DTO) format. 
 * It includes fields for the researcher's name, surname, second surname, and
 * university. It's annotated with Jakarta's validations to ensure mandatory
 * fields are not blank and comply with certain size and format restrictions.
 * Additionally, it uses the @JsonIgnore annotation to prevent the id field
 * from being serialized in JSON responses, possibly because it's considered
 * sensitive information or unwanted in the output. Lombok is employed to
 * automatically generate getters, setters, and a no-argument constructor.
 */

@Setter
@Getter
@NoArgsConstructor
public class ResearcherDTO {
    
    @JsonIgnore
    private int id;
    
    @NotBlank(message = "Name is mandatory")
    @NotNull(message = "Name is mandatory")
    @Size(min = 3, max = 64, message = "Name must be between 3 and 64 characters")
    @Pattern(regexp = "^[^0-9\\s]+$", message = "Name must not contain numbers or spaces")
    private String name;
    
    @NotBlank(message = "Surname is mandatory")
    @NotNull(message = "Surname is mandatory")
    @Size(min = 3, max = 64, message = "Surname must be between 3 and 64 characters")
    @Pattern(regexp = "^[^0-9\\s]+$", message = "Surname must not contain numbers or spaces")
    private String surname;
    
    @NotBlank(message = "Second surname is mandatory")
    @NotNull(message = "Second surname is mandatory")
    @Size(min = 3, max = 64, message = "Second surname must be between 3 and 64 characters")
    @Pattern(regexp = "^[^0-9\\s]+$", message = "Second surname must not contain numbers or spaces")
    private String secSurname;
    
    @NotBlank(message = "University is mandatory")
    @NotNull(message = "University is mandatory")
    @Size(min = 3, max = 128, message = "University must be between 3 and 128 characters")
    private String university;
}
