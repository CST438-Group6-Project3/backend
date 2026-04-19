package com.HiddenGems.backend.controller;

import com.HiddenGems.backend.dto.location.CreateLocationRequest;
import com.HiddenGems.backend.dto.location.LocationResponse;
import com.HiddenGems.backend.service.LocationService;
import jakarta.validation.Valid;
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
}