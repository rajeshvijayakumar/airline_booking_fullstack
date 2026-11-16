package com.vitara.flight.service.interservice;


import com.vitara.common.response.AirlineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AirlineIntegrationService {

    private final RestTemplate restTemplate;

    public AirlineResponse getAirlineById(Long id){

        String url = "http://localhost:9080/api/airlines/"+id;

        return restTemplate.getForObject(url, AirlineResponse.class);
    }
}
