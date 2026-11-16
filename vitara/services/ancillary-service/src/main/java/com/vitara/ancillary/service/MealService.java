package com.vitara.ancillary.service;

import com.vitara.common.request.MealRequest;
import com.vitara.common.response.MealResponse;

import java.util.List;

public interface MealService {

    MealResponse createMeal(Long userId, MealRequest request) throws Exception;

    MealResponse getMealById(Long id) throws Exception;

    MealResponse updateMeal(Long userId, Long id, MealRequest request) throws Exception;

    List<MealResponse> getByAirlineId(Long userId);

    void deleteMeal(Long id) throws Exception;

    MealResponse updateAvailability(Long id, Boolean availability) throws Exception;

}
