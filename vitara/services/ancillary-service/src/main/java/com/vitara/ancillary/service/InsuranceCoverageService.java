package com.vitara.ancillary.service;


import com.vitara.common.request.InsuranceCoverageRequest;
import com.vitara.common.response.InsuranceCoverageResponse;

import java.util.List;

public interface InsuranceCoverageService {

    InsuranceCoverageResponse createCoverage(InsuranceCoverageRequest request) throws Exception;

    InsuranceCoverageResponse updateCoverage(Long id, InsuranceCoverageRequest request) throws Exception;

    void deleteCoverage(Long id) throws Exception;
    InsuranceCoverageResponse getCoverage(Long id) throws Exception;
    List<InsuranceCoverageResponse> getCoverageByAncillaryId(Long ancillaryId);
    List<InsuranceCoverageResponse> getActiveCoverageByAncillaryId(Long ancillaryId);
    List<InsuranceCoverageResponse> getAllCoverages();
}
