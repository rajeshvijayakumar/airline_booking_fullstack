package com.vitara.airline.repository;

import com.vitara.airline.entities.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    // 1. Used in createAircraft (Checks global uniqueness)
    boolean existsByCode(String code);

    // 2. FIX FOR UPDATE: Checks if code exists on ANY OTHER aircraft except the current one
    boolean existsByCodeAndIdNot(String code, Long id);

    // 3. Used in listAllAircraftByOwner
    List<Aircraft> findByAirlineId(Long airlineId);

    // 4. FIX FOR NULL CHECKS: Wraps the result in an Optional container safely
    Optional<Aircraft> findByIdAndAirlineId(Long id, Long airlineId);
}
