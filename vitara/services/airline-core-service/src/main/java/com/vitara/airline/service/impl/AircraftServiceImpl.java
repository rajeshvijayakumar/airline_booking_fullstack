package com.vitara.airline.service.impl;

import com.vitara.airline.entities.Aircraft;
import com.vitara.airline.entities.Airline;
import com.vitara.airline.mapper.AircraftMapper;
import com.vitara.airline.repository.AircraftRepository;
import com.vitara.airline.repository.AirlineRepository;
import com.vitara.airline.service.AircraftService;
import com.vitara.common.request.AircraftRequest;
import com.vitara.common.response.AircraftResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;

    @Override
    public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception {
        Airline airline=airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        ()->new Exception("airline not exist for this ownerId")
                );

        Aircraft aircraft= AircraftMapper.toEntity(request,airline);

        if(aircraftRepository.existsByCode(aircraft.getCode())){
            throw new Exception("code already exist with another aircraft");
        }

        if(aircraft.getSeatingCapacity()<aircraft.getTotalSeats()){
            throw new Exception("seating capacity can't exceed to total seat");
        }
        return AircraftMapper.toResponse(
                aircraftRepository.save(aircraft)
        );
    }

    @Override
    public AircraftResponse getAircraftById(Long id) throws Exception {
        Aircraft aircraft= aircraftRepository.findById(id)
                .orElseThrow(
                        ()->new Exception("Aircraft not exist with id")
                );
        return AircraftMapper.toResponse(aircraft);
    }

    @Override
    public List<AircraftResponse> listAllAircraftByOwner(Long ownerId) throws Exception {
        Airline airline=airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        ()-> new Exception("this owner don't have airline")
                );

        return aircraftRepository.findByAirlineId(airline.getId())
                .stream()
                .map(AircraftMapper::toResponse).toList();
    }

    @Override
    public AircraftResponse updateAircraft(Long id, AircraftRequest request, Long ownerId) throws Exception {
        Airline airline=airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        ()-> new Exception("this owner don't have airline")
                );
        Aircraft aircraft=aircraftRepository.findByIdAndAirlineId(id,airline.getId());
        if(aircraft==null){
            throw new Exception("Aircraft not exist with id");
        }
        if(request.getCode()!=null
                && !aircraft.getCode().equals(request.getCode())
                && aircraftRepository.existsByCode(request.getCode())){
            throw new Exception("code already exist with another aircraft");
        }
        AircraftMapper.updateEntity(aircraft, request);
        return AircraftMapper.toResponse(
                aircraftRepository.save(aircraft)
        );
    }

    @Override
    public void deleteAircraft(Long id, Long ownerId) throws Exception {
        Airline airline=airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        ()-> new Exception("this owner don't have airline")
                );
        Aircraft aircraft=aircraftRepository.findByIdAndAirlineId(id,airline.getId());
        if(aircraft==null){
            throw new Exception("Aircraft not exist with id");
        }
        aircraftRepository.delete(aircraft);

    }
}
