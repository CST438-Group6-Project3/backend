package com.HiddenGems.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HiddenGems.backend.entity.CollectionItem;
import com.HiddenGems.backend.repository.CollectionItemRepository;

@Service
public class CollectionItemService {

    @Autowired
    private CollectionItemRepository repo;

    public void addItem(Long collectionId, Long locationId) {
        CollectionItem item = new CollectionItem();
        item.setCollectionId(collectionId);
        item.setLocationId(locationId);
        repo.save(item);
    }

    public void removeItem(Long collectionId, Long locationId) {
        repo.deleteByCollectionIdAndLocationId(collectionId, locationId);
    }
}