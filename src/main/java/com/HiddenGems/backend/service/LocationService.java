package com.HiddenGems.backend.service;

import com.HiddenGems.backend.dto.location.CreateLocationRequest;
import com.HiddenGems.backend.dto.location.LocationResponse;
import com.HiddenGems.backend.dto.location.UpdateLocationRequest;
import com.HiddenGems.backend.entity.Location;
import com.HiddenGems.backend.entity.User;
import com.HiddenGems.backend.repository.LocationRepository;
import com.HiddenGems.backend.repository.UserRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    public LocationService(LocationRepository locationRepository, UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    public LocationResponse createLocation(CreateLocationRequest request) {
        // .orElseThrow handles the case that there is no user with said ID (findById
        // returns Optional<User>)
        // TODO: Currently uses createdById for testing, get user from auth context when
        // authentication is added
        User user = userRepository.findById(request.getCreatedById())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Location location = new Location();
        location.setName(request.getName());
        location.setDescription(request.getDescription());
        location.setCategory(request.getCategory());
        location.setTags(request.getTags() == null ? new String[]{} : request.getTags());
        location.setImageUrls(request.getImageUrls() == null ? new String[]{} : request.getImageUrls());
        location.setLat(request.getLat());
        location.setLng(request.getLng());
        location.setCreatedBy(user);

        Location saved = locationRepository.save(location);
        return new LocationResponse(saved);
    }

    public List<LocationResponse> getLocations(Location.Category category, Location.Status status) {
        List<Location> locations;

        if (category != null && status != null) {
            locations = locationRepository.findByCategoryAndStatus(category, status);
        } else if (category != null) {
            locations = locationRepository.findByCategory(category);
        } else if (status != null) {
            locations = locationRepository.findByStatus(status);
        } else {
            locations = locationRepository.findAll();
        }

        return locations.stream()
                .map(LocationResponse::new)
                .toList();
    }

    public LocationResponse getLocationById(UUID id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));

        return new LocationResponse(location);
    }

    public LocationResponse updateLocation(UUID id, UpdateLocationRequest request) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));

        if (request.getName() != null) {
            location.setName(request.getName());
        }
        if (request.getDescription() != null) {
            location.setDescription(request.getDescription());
        }
        if (request.getCategory() != null) {
            location.setCategory(request.getCategory());
        }
        if (request.getTags() != null) {
            location.setTags(request.getTags());
        }
        if (request.getImageUrls() != null) {
            location.setImageUrls(request.getImageUrls());
        }
        if (request.getLat() != null) {
            location.setLat(request.getLat());
        }
        if (request.getLng() != null) {
            location.setLng(request.getLng());
        }
        Location saved = locationRepository.save(location);
        return new LocationResponse(saved);
    }

    public void deleteLocation(UUID id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));

        locationRepository.delete(location);
    }
}
