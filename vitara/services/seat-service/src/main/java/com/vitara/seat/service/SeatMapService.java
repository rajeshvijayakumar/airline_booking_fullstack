package com.vitara.seat.service;

import com.vitara.common.request.SeatMapRequest;
import com.vitara.common.response.SeatMapResponse;

public interface SeatMapService {

    SeatMapResponse createSeatMap(Long userId, SeatMapRequest request) throws Exception;
    SeatMapResponse getSeatMapById(Long id) throws Exception;
    SeatMapResponse getSeatMapByCabinClass(Long cabinId);
    SeatMapResponse updateSeatMap(Long id, SeatMapRequest request) throws Exception;
    void deleteSeatMap(Long id) throws Exception;
}
