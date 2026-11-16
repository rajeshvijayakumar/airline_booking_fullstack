package com.vitara.seat.service;

import com.vitara.common.enums.SeatAvailabilityStatus;
import com.vitara.common.response.SeatInstanceResponse;

import java.util.List;

public interface SeatInstanceService {

    Double calculateSeatPrice(List<Long> seatInstanceIds);
    SeatInstanceResponse updateSeatInstanceStatus(Long seatInstanceId,
                                                  SeatAvailabilityStatus status);
}
