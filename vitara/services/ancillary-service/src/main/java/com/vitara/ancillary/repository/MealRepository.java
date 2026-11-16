package com.vitara.ancillary.repository;

import com.vitara.ancillary.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findByAirlineId(Long airlineId);
    boolean existsByCodeAndAirlineId(String code, Long airlineId);
    boolean existsByAirlineIdAndCodeAndIdNot(Long airlineId, String code, Long id);
}
