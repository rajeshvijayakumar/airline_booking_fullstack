package com.vitara.ancillary.service.impl;

import com.vitara.ancillary.entities.Ancillary;
import com.vitara.ancillary.entities.FlightCabinAncillary;
import com.vitara.ancillary.entities.InsuranceCoverage;
import com.vitara.ancillary.mapper.FlightCabinAncillaryMapper;
import com.vitara.ancillary.mapper.InsuranceCoverageMapper;
import com.vitara.ancillary.repository.AncillaryRepository;
import com.vitara.ancillary.repository.FlightCabinAncillaryRepository;
import com.vitara.ancillary.repository.InsuranceCoverageRepository;
import com.vitara.ancillary.service.FlightCabinAncillaryService;
import com.vitara.common.enums.AncillaryType;
import com.vitara.common.request.FlightCabinAncillaryRequest;
import com.vitara.common.response.FlightCabinAncillaryResponse;
import com.vitara.common.response.InsuranceCoverageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightCabinAncillaryServiceImpl implements FlightCabinAncillaryService {

    private final FlightCabinAncillaryRepository flightCabinAncillaryRepository;
    private final AncillaryRepository ancillaryRepository;
    private final InsuranceCoverageRepository insuranceCoverageRepository;

    @Override
    public FlightCabinAncillaryResponse create(FlightCabinAncillaryRequest request) throws Exception {
        Ancillary ancillary=ancillaryRepository.findById(request.getAncillaryId())
                .orElseThrow(()->new Exception("ancillary not found"));

        FlightCabinAncillary fCabinAncillary=FlightCabinAncillary.builder()
                .flightId(request.getFlightId())
                .cabinClassId(request.getCabinClassId())
                .ancillary(ancillary)
                .available(request.getAvailable())
                .maxQuantity(request.getMaxQuantity())
                .price(request.getPrice())
                .includedInFare(request.getIncludedInFare())
                .build();

        FlightCabinAncillary saved=flightCabinAncillaryRepository.save(fCabinAncillary);

        return convertToResponse(saved);
    }

    @Override
    public FlightCabinAncillaryResponse getById(Long id) throws Exception {
        FlightCabinAncillary flightCabinAncillary=flightCabinAncillaryRepository.findById(id)
                .orElseThrow(()->new Exception("flight cabin ancillary not found"));
        return convertToResponse(flightCabinAncillary);
    }

    @Override
    public List<FlightCabinAncillaryResponse> getByFlightAndCabinClass(Long flightId, Long cabinClassId) {
        return flightCabinAncillaryRepository.findByFlightIdAndCabinClassId(
                flightId,cabinClassId
        ).stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<FlightCabinAncillaryResponse> getAllByIds(List<Long> ids) {
        return flightCabinAncillaryRepository.findAllById(ids)
                .stream()
                .map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public FlightCabinAncillaryResponse getByFlightIdAndCabinClassIdAndType(
            Long flightId, Long cabinClassId,
            AncillaryType type) {
        FlightCabinAncillary flightCabinAncillary=
                flightCabinAncillaryRepository.findByFlightIdAndCabinClassIdAndAncillary_Type(
                        flightId,cabinClassId,type
                );
        return convertToResponse(flightCabinAncillary);
    }

    @Override
    public List<FlightCabinAncillaryResponse> getAllByFlightIdAndCabinClassIdAndType(Long flightId, Long cabinClassId, AncillaryType type) {
        return flightCabinAncillaryRepository.findAllByFlightIdAndCabinClassIdAndAncillary_Type(
                flightId,cabinClassId,type
        ).stream().map(
                this::convertToResponse
        ).collect(Collectors.toList());
    }

    @Override
    public FlightCabinAncillaryResponse update(Long id, FlightCabinAncillaryRequest request) throws Exception {
        FlightCabinAncillary flightCabinAncillary=flightCabinAncillaryRepository.findById(
                id
        ).orElseThrow(()->new Exception("Flight cabin ancillary not found"));

        flightCabinAncillary.setAvailable(request.getAvailable());
        flightCabinAncillary.setMaxQuantity(request.getMaxQuantity());
        flightCabinAncillary.setPrice(request.getPrice());
        flightCabinAncillary.setIncludedInFare(request.getIncludedInFare());

        FlightCabinAncillary saved=flightCabinAncillaryRepository.save(flightCabinAncillary);
        return convertToResponse(saved);
    }

    @Override
    public void delete(Long id) throws Exception {
        FlightCabinAncillary flightCabinAncillary=flightCabinAncillaryRepository.findById(
                id
        ).orElseThrow(()->new Exception("Flight cabin ancillary not found"));
        flightCabinAncillaryRepository.delete(flightCabinAncillary);
    }

    @Override
    public Double calculateAncillaryPrice(List<Long> ancillaryIds) {
        List<FlightCabinAncillary> ancillaries=flightCabinAncillaryRepository.findAllById(ancillaryIds);
        double totalPrice=0;
        for(FlightCabinAncillary ancillary:ancillaries){
            totalPrice+=ancillary.getPrice();
        }
        return totalPrice;
    }

    private FlightCabinAncillaryResponse convertToResponse(FlightCabinAncillary ancillary) {
        List<InsuranceCoverage> coverages=insuranceCoverageRepository
                .findByAncillaryId(ancillary.getId());
        List<InsuranceCoverageResponse> coverageResponses = coverages.stream().map(
                InsuranceCoverageMapper::toResponse
        ).toList();

        return FlightCabinAncillaryMapper.toResponse(ancillary,coverageResponses);
    }
}
