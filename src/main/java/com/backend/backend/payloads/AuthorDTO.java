package com.backend.backend.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuthorDTO {
    
    @JsonIgnore
    private int id;
    
    @NotNull(message = "The ID of the article is mandatory")
    private int ref_article;
    
    @NotNull(message = "The ID of the researcher is mandatory")
    private int ref_researcher;
}
