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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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
        request.setTags(new String[]{"test"});
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
}