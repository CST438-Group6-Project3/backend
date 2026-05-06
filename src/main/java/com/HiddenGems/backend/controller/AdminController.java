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

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if ("admin".equalsIgnoreCase(user.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete admins");
        }

        userRepository.delete(user);
    }

    @GetMapping("/locations/pending")
    public List<Location> getPendingLocations() {
        return locationRepository.findByStatus(Location.Status.pending);
    }

    @PutMapping("/locations/{id}/verify")
    public Location verifyLocation(@PathVariable UUID id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));

        location.setStatus(Location.Status.verified);
        return locationRepository.save(location);
    }

    @PutMapping("/locations/{id}/archive")
    public Location archiveLocation(@PathVariable UUID id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));

        location.setStatus(Location.Status.archived);
        return locationRepository.save(location);
    }
}