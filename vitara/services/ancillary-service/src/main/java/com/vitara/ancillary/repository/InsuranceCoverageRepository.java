package com.vitara.ancillary.repository;

import com.vitara.ancillary.entities.InsuranceCoverage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsuranceCoverageRepository extends JpaRepository<InsuranceCoverage, Long> {

    List<InsuranceCoverage> findByAncillaryId(Long ancillaryId);
    List<InsuranceCoverage> findByAncillaryIdAndActiveTrue(Long ancillaryId);
}
