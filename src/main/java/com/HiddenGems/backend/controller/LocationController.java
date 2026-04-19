package com.HiddenGems.backend.controller;

import com.HiddenGems.backend.dto.location.CreateLocationRequest;
import com.HiddenGems.backend.entity.Location;
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
    public ResponseEntity<Location> createLocation(@Valid @RequestBody CreateLocationRequest request) {
        Location created = locationService.createLocation(request);
        // TODO: Return response DTO once created instead of raw location entity
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}