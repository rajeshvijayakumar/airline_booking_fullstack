package com.vitara.airline.service;

import com.vitara.common.enums.AirlineStatus;
import com.vitara.common.request.AirlineRequest;
import com.vitara.common.response.AirlineDropdownItem;
import com.vitara.common.response.AirlineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {

    // FIX: Added throws Exception to allow validation checks on creation
    AirlineResponse createAirline(AirlineRequest request, Long ownerId) throws Exception;

    AirlineResponse getAirlineByOwner(Long ownerId) throws Exception;

    AirlineResponse getAirlineById(Long id) throws Exception;

    Page<AirlineResponse> getAllAirlines(Pageable pageable);

    AirlineResponse updateAirline(AirlineRequest request, Long ownerId) throws Exception;

    void deleteAirline(Long id, Long ownerId) throws Exception;

    AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status) throws Exception;

    List<AirlineDropdownItem> getAirlineDropdown();
}
