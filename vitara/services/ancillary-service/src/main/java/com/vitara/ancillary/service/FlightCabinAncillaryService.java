package com.vitara.ancillary.service;

import com.vitara.common.enums.AncillaryType;
import com.vitara.common.request.FlightCabinAncillaryRequest;
import com.vitara.common.response.FlightCabinAncillaryResponse;

import java.util.List;

public interface FlightCabinAncillaryService {

    FlightCabinAncillaryResponse create(FlightCabinAncillaryRequest request) throws Exception;
    FlightCabinAncillaryResponse getById(Long id) throws Exception;
    List<FlightCabinAncillaryResponse> getByFlightAndCabinClass(Long flightId, Long cabinClassId);
    List<FlightCabinAncillaryResponse> getAllByIds(List<Long> ids);
    FlightCabinAncillaryResponse getByFlightIdAndCabinClassIdAndType(Long flightId,
                                                                     Long cabinClassId,
                                                                     AncillaryType type
    );
    List<FlightCabinAncillaryResponse> getAllByFlightIdAndCabinClassIdAndType(
            Long flightId, Long cabinClassId, AncillaryType type
    );
    FlightCabinAncillaryResponse update(Long id, FlightCabinAncillaryRequest request) throws Exception;

    void delete(Long id) throws Exception;
    Double calculateAncillaryPrice(List<Long> ancillaryIds);


}
