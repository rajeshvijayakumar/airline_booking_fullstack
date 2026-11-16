package com.vitara.booking.service;

import com.vitara.booking.entities.Passenger;
import com.vitara.common.request.PassengerRequest;

public interface PassengerService {

    Passenger createPassenger(PassengerRequest request, Long userId);
}
