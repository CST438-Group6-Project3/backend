package com.HiddenGems.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import com.HiddenGems.backend.entity.CollectionItem;
import com.HiddenGems.backend.repository.CollectionItemRepository;

@Service
public class CollectionItemService {

    @Autowired
    private CollectionItemRepository repo;

    public void addItem(Long collectionId, UUID locationId) {

        // 🔒 prevent duplicates
        if (repo.existsByCollectionIdAndLocationId(collectionId, locationId)) {
            return;
        }

        CollectionItem item = new CollectionItem();
        item.setCollectionId(collectionId);
        item.setLocationId(locationId);

        repo.save(item);
    }

    public void removeItem(Long collectionId, UUID locationId) {
        repo.deleteByCollectionIdAndLocationId(collectionId, locationId);
    }
}