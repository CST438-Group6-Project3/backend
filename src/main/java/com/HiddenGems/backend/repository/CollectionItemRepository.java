package com.HiddenGems.backend.repository;

import com.HiddenGems.backend.entity.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CollectionItemRepository extends JpaRepository<CollectionItem, UUID> {

    //Find all items by collection ID
    List<CollectionItem> findByCollection_Id(UUID collectionId);

    // Delete specific item
    void deleteByCollection_IdAndLocation_Id(UUID collectionId, UUID locationId);

    // Prevent duplicates
    boolean existsByCollection_IdAndLocation_Id(UUID collectionId, UUID locationId);
}