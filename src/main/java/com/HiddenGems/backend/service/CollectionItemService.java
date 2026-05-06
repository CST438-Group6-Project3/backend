package com.HiddenGems.backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HiddenGems.backend.entity.Collection;
import com.HiddenGems.backend.entity.CollectionItem;
import com.HiddenGems.backend.entity.Location;
import com.HiddenGems.backend.repository.CollectionItemRepository;
import com.HiddenGems.backend.repository.CollectionRepository;
import com.HiddenGems.backend.repository.LocationRepository;
import java.util.Objects;
import com.HiddenGems.backend.dto.location.LocationResponse;

@Service
public class CollectionItemService {

    @Autowired
    private CollectionItemRepository repo;

    @Autowired
    private CollectionRepository collectionRepo;

    @Autowired
    private LocationRepository locationRepo;

    // 🔥 Add item
    public void addItem(UUID collectionId, UUID locationId) {

        // ✅ Fetch entities
        Collection collection = collectionRepo.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collection not found"));

        Location location = locationRepo.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        // ✅ Prevent duplicates
        boolean exists = repo.existsByCollection_IdAndLocation_Id(collectionId, locationId);
        if (exists) return;

        // ✅ Create relationship
        CollectionItem item = new CollectionItem();
        item.setCollection(collection);
        item.setLocation(location);

        repo.save(item);
    }

    // 🔥 Remove item
    public void removeItem(UUID collectionId, UUID locationId) {
        repo.deleteByCollection_IdAndLocation_Id(collectionId, locationId);
    }

    // 🔥 Get all locations in a collection
    public List<LocationResponse> getItems(UUID collectionId) {
    return repo.findByCollection_Id(collectionId)
            .stream()
            .map(CollectionItem::getLocation)
            .filter(Objects::nonNull) // 🔥 prevents crashes
            .map(LocationResponse::new) // 🔥 convert to DTO
            .toList();
}
}