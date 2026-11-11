package com.vitara.airline.controller;

import com.vitara.airline.service.AirlineService;
import com.vitara.common.enums.AirlineStatus;
import com.vitara.common.request.AirlineRequest;
import com.vitara.common.response.AirlineDropdownItem;
import com.vitara.common.response.AirlineResponse;
import com.vitara.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;

    @PostMapping
    public ResponseEntity<AirlineResponse> createAirline(
            @Valid @RequestBody AirlineRequest airlineRequest,
            @RequestHeader("X-User-Id") Long userId) throws Exception { // FIX: Added throws Exception to match service interface

        return ResponseEntity.status(HttpStatus.CREATED).body(
                airlineService.createAirline(airlineRequest, userId)
        );
    }

    @GetMapping("/admin")
    public ResponseEntity<AirlineResponse> getAirlineByOwner(
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.ok(airlineService.getAirlineByOwner(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineResponse> getAirlineById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(airlineService.getAirlineById(id));
    }

    @GetMapping
    public ResponseEntity<Page<AirlineResponse>> getAllAirlines(Pageable pageable) {
        return ResponseEntity.ok(airlineService.getAllAirlines(pageable));
    }

    @GetMapping("/dropdown")
    public ResponseEntity<List<AirlineDropdownItem>> getAirlinesForDropdown() {
        return ResponseEntity.ok(airlineService.getAirlineDropdown());
    }

    @PutMapping
    public ResponseEntity<AirlineResponse> updateAirline(
            @Valid @RequestBody AirlineRequest request,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.ok(airlineService.updateAirline(request, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAirline(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        airlineService.deleteAirline(id, userId);
        ApiResponse res = new ApiResponse("Airline deleted successfully");
        return ResponseEntity.ok(res);
    }

    // CLEANER REST DESIGN: Replaced individual endpoints with a single dynamic status modifier route
    @PatchMapping("/{id}/status")
    public ResponseEntity<AirlineResponse> changeAirlineStatus(
            @PathVariable Long id,
            @RequestParam AirlineStatus status) throws Exception {
        return ResponseEntity.ok(airlineService.changeStatusByAdmin(id, status));
    }
}
