package com.vitara.booking.service.impl;

import com.vitara.booking.entities.Passenger;
import com.vitara.booking.mapper.PassengerMapper;
import com.vitara.booking.repository.PassengerRepository;
import com.vitara.booking.service.PassengerService;
import com.vitara.common.request.PassengerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {


    private final PassengerRepository passengerRepository;

    @Override
    public Passenger createPassenger(PassengerRequest request, Long userId) {

        Passenger passenger = PassengerMapper.toEntity(request);
        passenger.setPrimaryUserId(userId);
        Passenger saved=passengerRepository.save(passenger);
        return saved;
    }


}
