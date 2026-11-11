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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;

    @Override
    @Transactional
    public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline does not exist for this owner ID"));

        Aircraft aircraft = AircraftMapper.toEntity(request, airline);

        if (aircraftRepository.existsByCode(aircraft.getCode())) {
            throw new Exception("Aircraft code already exists");
        }

        if (aircraft.getSeatingCapacity() > aircraft.getTotalSeats()) {
            throw new Exception("Seating capacity cannot exceed total seats");
        }

        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Override
    @Transactional(readOnly = true)
    public AircraftResponse getAircraftById(Long id) throws Exception {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new Exception("Aircraft does not exist with ID: " + id));
        return AircraftMapper.toResponse(aircraft);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AircraftResponse> listAllAircraftByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("This owner does not have an airline"));

        return aircraftRepository.findByAirlineId(airline.getId())
                .stream()
                .map(AircraftMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public AircraftResponse updateAircraft(Long id, AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("This owner does not have an airline"));

        // FIX: Handled as Optional from Repository to eliminate silent failures or bugs
        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId())
                .orElseThrow(() -> new Exception("Aircraft does not exist with the given ID for your airline"));

        // FIX: Uses code exclusion constraint check (ignoring current ID)
        if (request.getCode() != null
                && !aircraft.getCode().equals(request.getCode())
                && aircraftRepository.existsByCodeAndIdNot(request.getCode(), id)) {
            throw new Exception("Aircraft code already exists with another aircraft");
        }

        AircraftMapper.updateEntity(aircraft, request);

        // FIX: Enforces business logic check after DTO mapping assigns values
        if (aircraft.getSeatingCapacity() > aircraft.getTotalSeats()) {
            throw new Exception("Seating capacity cannot exceed total seats");
        }

        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Override
    @Transactional
    public void deleteAircraft(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("This owner does not have an airline"));

        // FIX: Safely handling removal confirmation
        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId())
                .orElseThrow(() -> new Exception("Aircraft does not exist with the given ID for your airline"));

        aircraftRepository.delete(aircraft);
    }
}
