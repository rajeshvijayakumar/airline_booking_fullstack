package com.vitara.ancillary.service;

import com.vitara.common.request.FlightMealRequest;
import com.vitara.common.response.FlightMealResponse;

import java.util.List;

public interface FlightMealService {

    FlightMealResponse createFlightMeal(FlightMealRequest request) throws Exception;
    FlightMealResponse getFlightMealById(Long id) throws Exception;
    List<FlightMealResponse> getByFlightId(Long flightId);
    List<FlightMealResponse> getAllByIds(List<Long> ids);
    FlightMealResponse updateFlightMeal(Long id, FlightMealRequest request) throws Exception;
    void deleteFlightMeal(Long id) throws Exception;
    FlightMealResponse updateFlightMealAvailability(Long id, Boolean availability) throws Exception;
    Double calculateMealPrice(List<Long> mealIds);
}
