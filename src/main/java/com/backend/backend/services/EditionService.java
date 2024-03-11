package com.backend.backend.services;

import com.backend.backend.payloads.EditionDTO;
import com.backend.backend.utils.ApiResponse;

public interface EditionService {
    
    // STANDARD CRUD OPERATIONS
    ApiResponse createEdition (EditionDTO edition);
    ApiResponse updateEdition (Integer id, EditionDTO edition);
    ApiResponse getEditionByID (Integer id);
    ApiResponse getEditionsByYearPaginated (Integer year, Integer page, Integer size);
    ApiResponse getEditionsBetweenYearsPaginated (Integer startYear, Integer endYear, Integer page, Integer size);
    ApiResponse getEditionsByCityPaginated (String city, Integer page, Integer size);
    ApiResponse getEditionsByDatePaginated (String date, Integer page, Integer size);
    ApiResponse getAllEditionsPaginated(Integer page, Integer size);
    ApiResponse deleteEdition (Integer id);

}
