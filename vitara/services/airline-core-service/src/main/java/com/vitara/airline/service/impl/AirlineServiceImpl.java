package com.vitara.airline.service.impl;

import com.vitara.airline.entities.Airline;
import com.vitara.airline.mapper.AirlineMapper;
import com.vitara.airline.repository.AirlineRepository;
import com.vitara.airline.service.AirlineService;
import com.vitara.common.enums.AirlineStatus;
import com.vitara.common.request.AirlineRequest;
import com.vitara.common.response.AirlineDropdownItem;
import com.vitara.common.response.AirlineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;

    @Override
    @Transactional
    public AirlineResponse createAirline(AirlineRequest request, Long ownerId) throws Exception {
        // FIX: Ensure an owner doesn't already have an airline registered
        if (airlineRepository.findByOwnerId(ownerId).isPresent()) {
            throw new Exception("An airline is already registered for this owner ID: " + ownerId);
        }

        Airline airline = AirlineMapper.toEntity(request, ownerId);

        // Defaulting status to PENDING or ACTIVE depending on requirements if not set by mapper
        if (airline.getStatus() == null) {
            airline.setStatus(AirlineStatus.INACTIVE);
        }

        Airline savedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    @Transactional(readOnly = true)
    public AirlineResponse getAirlineByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found for owner ID: " + ownerId));
        return AirlineMapper.toResponse(airline);
    }

    @Override
    @Transactional(readOnly = true)
    public AirlineResponse getAirlineById(Long id) throws Exception {
        // FIX: Exception message previously said "ownerId" instead of "airline ID"
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new Exception("Airline not found for airline ID: " + id));
        return AirlineMapper.toResponse(airline);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AirlineResponse> getAllAirlines(Pageable pageable) {
        return airlineRepository.findAll(pageable)
                .map(AirlineMapper::toResponse);
    }

    @Override
    @Transactional
    public AirlineResponse updateAirline(AirlineRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found for owner ID: " + ownerId));

        AirlineMapper.updateEntity(airline, request);
        Airline savedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    @Transactional
    public void deleteAirline(Long id, Long ownerId) throws Exception {
        // FIX: Verify the airline belongs to the actual owner before allowing deletion
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found for owner ID: " + ownerId));

        if (!airline.getId().equals(id)) {
            throw new Exception("Unauthorized deletion request: This airline does not belong to the owner");
        }

        airlineRepository.delete(airline);
    }

    @Override
    @Transactional
    public AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status) throws Exception {
        // FIX: Exception message previously said "ownerId" instead of "airline ID"
        Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> new Exception("Airline not found for airline ID: " + airlineId));

        airline.setStatus(status);
        Airline updatedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(updatedAirline);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirlineDropdownItem> getAirlineDropdown() {
        return airlineRepository.findByStatus(AirlineStatus.ACTIVE)
                .stream()
                .map(a -> AirlineDropdownItem.builder()
                        .id(a.getId())
                        .name(a.getName())
                        .iataCode(a.getIataCode())
                        .icaoCode(a.getIcaoCode())
                        .logoUrl(a.getLogoUrl())
                        .build())
                .toList();
    }
}
