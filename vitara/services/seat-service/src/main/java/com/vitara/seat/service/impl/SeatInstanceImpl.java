package com.vitara.seat.service.impl;

import com.vitara.common.enums.SeatAvailabilityStatus;
import com.vitara.common.response.SeatInstanceResponse;
import com.vitara.seat.entities.SeatInstance;
import com.vitara.seat.mapper.SeatInstanceMapper;
import com.vitara.seat.repository.SeatInstanceRepository;
import com.vitara.seat.service.SeatInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatInstanceImpl implements SeatInstanceService {

    private final SeatInstanceRepository seatInstanceRepository;

    @Override
    public Double calculateSeatPrice(List<Long> seatInstanceIds) {
        List<SeatInstance> seatInstances=seatInstanceRepository.findAllById(seatInstanceIds);

        double price=0;
        for(SeatInstance si:seatInstances){
            double seatPremium=si.getPremiumSupercharge()!=null?
                    si.getPremiumSupercharge():0;
            price+=seatPremium;
        }
        return price;
    }

    @Override
    public SeatInstanceResponse updateSeatInstanceStatus(Long seatInstanceId,
                                                         SeatAvailabilityStatus status) {
        SeatInstance seatInstance=seatInstanceRepository.findById(seatInstanceId).orElse(null);
        if(seatInstance==null){return null;}
        seatInstance.setStatus(status);
        seatInstanceRepository.save(seatInstance);
        return SeatInstanceMapper.toResponse(seatInstance);
    }
}
