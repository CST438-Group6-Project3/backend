package com.HiddenGems.backend.service;

import com.HiddenGems.backend.dto.location.CreateLocationRequest;
import com.HiddenGems.backend.dto.location.LocationResponse;
import com.HiddenGems.backend.entity.Location;
import com.HiddenGems.backend.entity.User;
import com.HiddenGems.backend.repository.LocationRepository;
import com.HiddenGems.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

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
        // TODO: Currently uses createdById for testing, get user from auth context when authentication is added
        User user = userRepository.findById(request.getCreatedById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Location location = new Location();
        location.setName(request.getName());
        location.setDescription(request.getDescription());
        location.setCategory(request.getCategory());
        location.setTags(request.getTags());
        location.setLat(request.getLat());
        location.setLng(request.getLng());
        location.setCreatedBy(user);

        Location saved = locationRepository.save(location);
        return new LocationResponse(saved);
    }
}