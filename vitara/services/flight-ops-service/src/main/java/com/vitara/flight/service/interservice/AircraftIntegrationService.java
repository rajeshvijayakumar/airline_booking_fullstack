package com.vitara.flight.service.interservice;


import com.vitara.common.response.AircraftResponse;
import com.vitara.common.response.AirlineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AircraftIntegrationService {

    private final RestTemplate restTemplate;

    public AircraftResponse getAircraftById(Long id){

        String url = "http://localhost:9080/api/aircrafts/"+id;

        return restTemplate.getForObject(url, AircraftResponse.class);
    }
}
