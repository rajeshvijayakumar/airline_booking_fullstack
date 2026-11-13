package com.vitara.seat.service;

import com.vitara.common.request.SeatRequest;
import com.vitara.common.response.SeatResponse;

import java.util.List;

public interface SeatService {
    void generateSeats(Long seatMapId) throws Exception;
    List<SeatResponse> getAll();
    SeatResponse updateSeats(Long seatId, SeatRequest request);
}
