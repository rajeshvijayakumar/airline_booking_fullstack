package com.vitara.pricing.service;

import com.vitara.common.request.FareRulesRequest;
import com.vitara.common.response.FareRulesResponse;

import java.util.List;

public interface FareRulesService {

    FareRulesResponse createFareRules(FareRulesRequest request) throws Exception;
    FareRulesResponse getFareRulesById(Long id) throws Exception;
    FareRulesResponse getFareRulesByFareId(Long fareId);
    List<FareRulesResponse> getFareRulesByAirlineId(Long airlineId);
    FareRulesResponse updateFareRules(Long id, FareRulesRequest request) throws Exception;
    void deleteFareRules(Long id);
}
