package com.HiddenGems.backend.controller;

import com.HiddenGems.backend.dto.location.CreateLocationRequest;
import com.HiddenGems.backend.dto.location.LocationResponse;
import com.HiddenGems.backend.entity.Location;
import com.HiddenGems.backend.service.LocationService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(@Valid @RequestBody CreateLocationRequest request) {
        LocationResponse created = locationService.createLocation(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LocationResponse>> getLocations(
            @RequestParam(required = false) Location.Category category,
            @RequestParam(required = false) Location.Status status) {
        List<LocationResponse> locations = locationService.getLocations(category, status);
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> getLocationById(@PathVariable UUID id) {
        LocationResponse location = locationService.getLocationById(id);
        return ResponseEntity.ok(location);
    }
}