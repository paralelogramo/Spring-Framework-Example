package com.backend.backend.services;

import com.backend.backend.payloads.AuthorDTO;
import com.backend.backend.utils.ApiResponse;

public interface AuthorService {

        // CRUD Methods
        ApiResponse createAuthor (AuthorDTO author);
        ApiResponse updateAuthor (AuthorDTO author, Integer id);
        ApiResponse getAuthorByID (Integer id);
        ApiResponse getAllAuthorsPaginated (Integer page, Integer size);
        ApiResponse deleteAuthor (Integer id);

}
