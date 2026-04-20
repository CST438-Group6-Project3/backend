package com.HiddenGems.backend.service;

import com.HiddenGems.backend.dto.location.CreateLocationRequest;
import com.HiddenGems.backend.dto.location.LocationResponse;
import com.HiddenGems.backend.entity.Location;
import com.HiddenGems.backend.entity.User;
import com.HiddenGems.backend.repository.LocationRepository;
import com.HiddenGems.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LocationService locationService;

    private UUID userId;
    private User user;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new User();
        user.setId(userId);
        user.setName("test1");
        user.setEmail("test1@test.com");
    }

    @Test
    void createLocation_shouldReturnLocationResponse_whenRequestIsValid() {
        CreateLocationRequest request = new CreateLocationRequest();
        request.setName("Test Spot");
        request.setDescription("Testing");
        request.setCategory(Location.Category.scenic);
        request.setTags(new String[] { "test" });
        request.setLat(36.6);
        request.setLng(-121.9);
        request.setCreatedById(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Location saved = new Location();
        saved.setId(UUID.randomUUID());
        saved.setName(request.getName());
        saved.setDescription(request.getDescription());
        saved.setCategory(request.getCategory());
        saved.setTags(request.getTags());
        saved.setLat(request.getLat());
        saved.setLng(request.getLng());
        saved.setCreatedBy(user);

        when(locationRepository.save(any(Location.class))).thenReturn(saved);

        LocationResponse response = locationService.createLocation(request);

        assertNotNull(response);
        assertEquals("Test Spot", response.getName());
        assertEquals(userId, response.getCreatedById());

        verify(userRepository).findById(userId);
        verify(locationRepository).save(any(Location.class));
    }

    @Test
    void createLocation_shouldThrow_whenUserNotFound() {
        UUID missingUserId = UUID.randomUUID();

        CreateLocationRequest request = new CreateLocationRequest();
        request.setName("Test Spot");
        request.setCategory(Location.Category.scenic);
        request.setLat(36.6);
        request.setLng(-121.9);
        request.setCreatedById(missingUserId);

        when(userRepository.findById(missingUserId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> locationService.createLocation(request));

        assertEquals("User not found", ex.getMessage());
        verify(locationRepository, never()).save(any());
    }

    @Test
    void getLocationById_shouldReturnLocationReaponse_whenExists() {
        UUID locationId = UUID.randomUUID();

        // create location
        Location location = new Location();
        location.setId(locationId);
        location.setName("Scenic Spot");
        location.setDescription("Nice view");
        location.setCategory(Location.Category.scenic);
        location.setTags(new String[] { "ocean", "sunset" });
        location.setLat(36.6);
        location.setLng(-121.9);
        location.setCreatedBy(user);

        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));

        LocationResponse response = locationService.getLocationById(locationId);

        // compare location fields to fields in response
        assertNotNull(response);
        assertEquals(locationId, response.getId());
        assertEquals("Scenic Spot", response.getName());
        assertEquals("Nice view", response.getDescription());
        assertEquals(Location.Category.scenic, response.getCategory());
        assertArrayEquals(new String[] { "ocean", "sunset" }, response.getTags());
        assertEquals(36.6, response.getLat());
        assertEquals(-121.9, response.getLng());
        assertEquals(userId, response.getCreatedById());

        verify(locationRepository).findById(locationId);
    }

    @Test
    void getLocationById_shouldThrow_whenLocationNotFound() {
        UUID missingLocationId = UUID.randomUUID();

        when(locationRepository.findById(missingLocationId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> locationService.getLocationById(missingLocationId));

        assertEquals("Location not found", ex.getMessage());
        verify(locationRepository).findById(missingLocationId);
    }
}