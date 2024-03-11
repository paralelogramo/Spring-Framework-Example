package com.backend.backend.payloads;

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
public class ArticleDTO {
    
    @JsonIgnore
    private int id;
    
    @NotNull(message = "Title is mandatory")
    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, max = 256, message = "Title must be between 3 and 256 characters")
    private String title;
    
    @NotNull(message = "The ID of the Edition is mandatory")
    private int ref_edition;
}
