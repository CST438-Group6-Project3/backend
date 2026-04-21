package com.HiddenGems.backend.service;

import com.HiddenGems.backend.dto.location.CreateLocationRequest;
import com.HiddenGems.backend.dto.location.LocationResponse;
import com.HiddenGems.backend.dto.location.UpdateLocationRequest;
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

import java.util.List;
import java.util.Arrays;
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

        assertEquals("404 NOT_FOUND \"User not found\"", ex.getMessage());
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

        assertEquals("404 NOT_FOUND \"Location not found\"", ex.getMessage());
        verify(locationRepository).findById(missingLocationId);
    }

    @Test
    void getAllLocations_shouldReturnMappedLocationResponses() {
        UUID locationId1 = UUID.randomUUID();
        UUID locationId2 = UUID.randomUUID();

        Location location1 = new Location();
        location1.setId(locationId1);
        location1.setName("Scenic Spot");
        location1.setDescription("Nice view");
        location1.setCategory(Location.Category.scenic);
        location1.setTags(new String[] { "ocean", "sunset" });
        location1.setLat(36.6);
        location1.setLng(-121.9);
        location1.setCreatedBy(user);

        Location location2 = new Location();
        location2.setId(locationId2);
        location2.setName("Trail Spot");
        location2.setDescription("Good walk");
        location2.setCategory(Location.Category.trail);
        location2.setTags(new String[] { "hike" });
        location2.setLat(36.7);
        location2.setLng(-121.8);
        location2.setCreatedBy(user);

        when(locationRepository.findAll()).thenReturn(Arrays.asList(location1, location2));

        List<LocationResponse> responses = locationService.getLocations(null, null);

        assertNotNull(responses);
        assertEquals(2, responses.size());

        assertEquals(locationId1, responses.get(0).getId());
        assertEquals("Scenic Spot", responses.get(0).getName());
        assertEquals(Location.Category.scenic, responses.get(0).getCategory());
        assertArrayEquals(new String[] { "ocean", "sunset" }, responses.get(0).getTags());
        assertEquals(userId, responses.get(0).getCreatedById());

        assertEquals(locationId2, responses.get(1).getId());
        assertEquals("Trail Spot", responses.get(1).getName());
        assertEquals(Location.Category.trail, responses.get(1).getCategory());
        assertArrayEquals(new String[] { "hike" }, responses.get(1).getTags());
        assertEquals(userId, responses.get(1).getCreatedById());

        verify(locationRepository).findAll();
    }

    @Test
    void getAllLocations_shouldReturnEmptyList_whenNoLocationsExist() {
        when(locationRepository.findAll()).thenReturn(List.of());

        List<LocationResponse> responses = locationService.getLocations(null, null);

        assertNotNull(responses);
        assertTrue(responses.isEmpty());

        verify(locationRepository).findAll();
    }

    @Test
    void updateLocation_shouldUpdateProvidedFields() {
        UUID locationId = UUID.randomUUID();

        Location existingLocation = new Location();
        existingLocation.setId(locationId);
        existingLocation.setName("Old Name");
        existingLocation.setDescription("Old description");
        existingLocation.setCategory(Location.Category.scenic);
        existingLocation.setTags(new String[] { "oldTag" });
        existingLocation.setLat(36.6);
        existingLocation.setLng(-121.9);
        existingLocation.setCreatedBy(user);

        UpdateLocationRequest request = new UpdateLocationRequest();
        request.setName("Updated Name");
        request.setDescription("Updated description");
        request.setCategory(Location.Category.trail);
        request.setTags(new String[] { "newTag1", "newTag2" });
        request.setLat(36.7);
        request.setLng(-121.8);

        when(locationRepository.findById(locationId)).thenReturn(Optional.of(existingLocation));
        when(locationRepository.save(any(Location.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LocationResponse response = locationService.updateLocation(locationId, request);

        assertNotNull(response);
        assertEquals(locationId, response.getId());
        assertEquals("Updated Name", response.getName());
        assertEquals("Updated description", response.getDescription());
        assertEquals(Location.Category.trail, response.getCategory());
        assertArrayEquals(new String[] { "newTag1", "newTag2" }, response.getTags());
        assertEquals(36.7, response.getLat());
        assertEquals(-121.8, response.getLng());
        assertEquals(userId, response.getCreatedById());

        verify(locationRepository).findById(locationId);
        verify(locationRepository).save(existingLocation);
    }

    @Test
    void updateLocation_shouldThrow_whenLocationNotFound() {
        UUID missingLocationId = UUID.randomUUID();

        UpdateLocationRequest request = new UpdateLocationRequest();
        request.setName("Updated Name");

        when(locationRepository.findById(missingLocationId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> locationService.updateLocation(missingLocationId, request));

        assertEquals("404 NOT_FOUND \"Location not found\"", ex.getMessage());
        verify(locationRepository).findById(missingLocationId);
        verify(locationRepository, never()).save(any(Location.class));
    }

    @Test
    void deleteLocation_shouldDeleteLocation_whenLocationExists() {
        UUID locationId = UUID.randomUUID();

        Location location = new Location();
        location.setId(locationId);
        location.setName("Test Spot");
        location.setCategory(Location.Category.scenic);
        location.setLat(36.6);
        location.setLng(-121.9);
        location.setCreatedBy(user);

        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));

        locationService.deleteLocation(locationId);

        verify(locationRepository).findById(locationId);
        verify(locationRepository).delete(location);
    }

    @Test
    void deleteLocation_shouldThrow_whenLocationNotFound() {
        UUID missingLocationId = UUID.randomUUID();

        when(locationRepository.findById(missingLocationId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> locationService.deleteLocation(missingLocationId));

        assertEquals("404 NOT_FOUND \"Location not found\"", ex.getMessage());
        verify(locationRepository).findById(missingLocationId);
        verify(locationRepository, never()).delete(any(Location.class));
    }
}