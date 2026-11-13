package com.vitara.pricing.service;


import com.vitara.common.request.FareRequest;
import com.vitara.common.response.FareResponse;
import com.vitara.pricing.entities.Fare;

import java.util.List;
import java.util.Map;

public interface FareService {
    FareResponse createFare(FareRequest request) throws Exception;
    FareResponse getFareById(Long id) throws Exception;
    List<FareResponse> getFaresByFlightIdAndCabinClassId(
            Long flightId, Long cabinClassId
    );
    FareResponse updateFare(Long id, FareRequest request) throws Exception;
    void deleteFare(Long id) throws Exception;
    List<Fare> getFares();

    Map<Long, FareResponse> getLowestFarePerFlight(
            List<Long> flightIds, Long cabinClassId
    );

    FareResponse getLowestFareForFlightAndCabin(Long flightId, Long cabinClassId);
    Map<Long, FareResponse> getFaresByIds(List<Long> ids);
}
