package com.vitara.flight.client;

import com.vitara.common.response.AircraftResponse;
import com.vitara.common.response.AirlineResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

//AIRLINE-CORE-SERVICE
//@FeignClient(name = "airline-core-service", url = "http://localhost:9080")
@FeignClient(name = "airline-core-service") // If you are using service-registry eureka, no need of passing the url..
public interface AirlineClient {

    @GetMapping("/api/airlines/{id}")
    AirlineResponse getAirlineById(
            @PathVariable Long id
    );

    @GetMapping("/api/aircrafts/{id}")
    AircraftResponse getAircraftById(@PathVariable("id") Long id);

    @GetMapping("/api/airlines/admin")
    AirlineResponse getAirlineByOwner(
            @RequestHeader("X-User-Id") Long userId);

}
