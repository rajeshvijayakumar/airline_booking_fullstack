package com.vitara.ancillary.service;

import com.vitara.common.request.AncillaryRequest;
import com.vitara.common.response.AncillaryResponse;

import java.util.List;

public interface AncillaryService {

    AncillaryResponse createAncillary(Long userId, AncillaryRequest request);

    AncillaryResponse getById(Long id) throws Exception;
    List<AncillaryResponse> getByAirlineId(Long userId);
    AncillaryResponse updateAncillary(Long id, AncillaryRequest request) throws Exception;
    void deleteAncillary(Long id) throws Exception;
}
