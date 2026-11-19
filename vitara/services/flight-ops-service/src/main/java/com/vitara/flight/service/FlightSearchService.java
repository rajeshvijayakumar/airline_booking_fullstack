package com.vitara.flight.service;

import com.vitara.common.request.FlightSearchRequest;
import com.vitara.common.response.FlightInstanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightSearchService {

    Page<FlightInstanceResponse> searchFlights(FlightSearchRequest request, Pageable pageable);
}
