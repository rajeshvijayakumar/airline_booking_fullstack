package com.vitara.flight.client;

import com.vitara.common.location.response.AirportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="location-service", url = "http://localhost:8080")
@FeignClient(name="location-service") // If you are using service-registry eureka, no need of passing the url..
public interface LocationClient {

    @GetMapping("/api/airports/{id}")
    AirportResponse getAirportById(@PathVariable Long id);
}
