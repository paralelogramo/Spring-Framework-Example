package com.backend.backend.services;

import com.backend.backend.payloads.ResearcherDTO;
import com.backend.backend.utils.ApiResponse;

public interface ResearcherService {

    // STANDARD CRUD OPERATIONS
    ApiResponse createResearcher(ResearcherDTO researcher);
    ApiResponse updateResearcher(Integer id, ResearcherDTO researcher);
    ApiResponse getResearcherByID(Integer id);
    ApiResponse deleteResearcher(Integer id);
    ApiResponse getAllResearchersPaginated(Integer page, Integer size);
    ApiResponse getResearchersByNamePaginated(String name, Integer page, Integer size);
    ApiResponse getResearchersBySurnamePaginated(String surname, Integer page, Integer size);
    ApiResponse getResearchersBySecSurnamePaginated(String secSurname, Integer page, Integer size);
    ApiResponse getResearchersByUniversityPaginated(String university, Integer page, Integer size);


    // CUSTOM OPERATIONS
    ApiResponse getArticlesFromResearcherByCompleteName(String name, String surname, String secSurname);
}
