package com.vitara.seat.repository;

import com.vitara.seat.entities.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatMapRepository extends JpaRepository<SeatMap,Long> {

    SeatMap findByCabinClassId(Long cabinClassId);

    boolean existsByAirlineIdAndCabinClassIdAndName(Long airlineId, Long cabinClassId, String name);
}
