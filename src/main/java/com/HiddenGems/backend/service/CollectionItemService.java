package com.HiddenGems.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.HiddenGems.backend.entity.CollectionItem;
import com.HiddenGems.backend.entity.Location;
import com.HiddenGems.backend.repository.CollectionItemRepository;
import com.HiddenGems.backend.repository.LocationRepository;

@Service
public class CollectionItemService {

    @Autowired
    private CollectionItemRepository repo;

    @Autowired
    private LocationRepository locationRepo;

    // ✅ ADD item
    public void addItem(Long collectionId, UUID locationId) {

        if (repo.existsByCollectionIdAndLocationId(collectionId, locationId)) {
            return;
        }

        CollectionItem item = new CollectionItem();
        item.setCollectionId(collectionId);
        item.setLocationId(locationId);

        repo.save(item);
    }

    // ✅ REMOVE item
    public void removeItem(Long collectionId, UUID locationId) {
        repo.deleteByCollectionIdAndLocationId(collectionId, locationId);
    }

    // 🔥 THIS IS THE MISSING METHOD
    public List<Location> getLocationsByCollection(Long collectionId) {
        List<UUID> locationIds = repo.findByCollectionId(collectionId)
                .stream()
                .map(CollectionItem::getLocationId)
                .toList();

        return locationRepo.findAllById(locationIds);
    }
}