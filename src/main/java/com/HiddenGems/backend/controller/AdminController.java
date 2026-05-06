package com.HiddenGems.backend.controller;

import com.HiddenGems.backend.entity.Location;
import com.HiddenGems.backend.entity.User;
import com.HiddenGems.backend.repository.LocationRepository;
import com.HiddenGems.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    public AdminController(UserRepository userRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/users/{id}/promote")
    public User promoteUser(@PathVariable UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setRole("admin");
        return userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if ("admin".equalsIgnoreCase(user.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete admins");
        }

        userRepository.delete(user);
    }

    @GetMapping("/locations/status/{status}")
    public List<LocationAdminResponse> getLocationsByStatus(@PathVariable Location.Status status) {
        return locationRepository.findByStatus(status)
                .stream()
                .map(LocationAdminResponse::from)
                .toList();
    }

    @GetMapping("/locations/pending")
    public List<LocationAdminResponse> getPendingLocations() {
        return getLocationsByStatus(Location.Status.pending);
    }

    @PutMapping("/locations/{id}/verify")
    public LocationAdminResponse verifyLocation(@PathVariable UUID id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));

        location.setStatus(Location.Status.verified);
        return LocationAdminResponse.from(locationRepository.save(location));
    }

    @PutMapping("/locations/{id}/archive")
    public LocationAdminResponse archiveLocation(@PathVariable UUID id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));

        location.setStatus(Location.Status.archived);
        return LocationAdminResponse.from(locationRepository.save(location));
    }

    public record LocationAdminResponse(
            UUID id,
            String name,
            String description,
            Location.Category category,
            String[] tags,
            String[] imageUrls,
            Double lat,
            Double lng,
            Location.Status status
    ) {
        public static LocationAdminResponse from(Location location) {
            return new LocationAdminResponse(
                    location.getId(),
                    location.getName(),
                    location.getDescription(),
                    location.getCategory(),
                    location.getTags(),
                    location.getImageUrls(),
                    location.getLat(),
                    location.getLng(),
                    location.getStatus()
            );
        }
    }
}