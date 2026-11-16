package com.vitara.ancillary.repository;

import com.vitara.ancillary.entities.Ancillary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AncillaryRepository extends JpaRepository<Ancillary,Long> {

    List<Ancillary> findByAirlineId(Long airlineId);
}
