package com.backend.backend.services;

import java.util.List;

import com.backend.backend.payloads.ConferenceDTO;
import com.backend.backend.utils.ApiResponse;

public interface ConferenceService {

    ApiResponse createConference (ConferenceDTO conference);

    ApiResponse updateConference (ConferenceDTO conference, Integer id);

    ApiResponse getConferenceByID (Integer id);

    ApiResponse getAllConferencesPaginated (Integer page, Integer size);

    ApiResponse deleteConference (Integer id);
    
    ApiResponse getConferencesByNamePaginated (String name, Integer page, Integer size);

}
