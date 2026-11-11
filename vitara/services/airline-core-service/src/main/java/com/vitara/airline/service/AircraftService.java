package com.vitara.airline.service;

import com.vitara.common.request.AircraftRequest;
import com.vitara.common.response.AircraftResponse;

import java.util.List;

public interface AircraftService {

    AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception;
    AircraftResponse getAircraftById(Long id) throws Exception;
    List<AircraftResponse> listAllAircraftByOwner(Long ownerId) throws Exception;
    AircraftResponse updateAircraft(Long id, AircraftRequest request, Long ownerId) throws Exception;
    void deleteAircraft(Long id, Long ownerId) throws Exception;

}
