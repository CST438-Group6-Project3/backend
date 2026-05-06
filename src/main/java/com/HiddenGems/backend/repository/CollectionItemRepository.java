package com.HiddenGems.backend.repository;

import com.HiddenGems.backend.entity.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CollectionItemRepository extends JpaRepository<CollectionItem, UUID> {

    // 🔥 Fetch collection items with locations (fixes lazy loading)
    @Query("""
        SELECT ci
        FROM CollectionItem ci
        JOIN FETCH ci.location l
        JOIN FETCH l.createdBy
        WHERE ci.collection.id = :collectionId
    """)
    List<CollectionItem> findWithLocationByCollectionId(UUID collectionId);

    // 🔥 Prevent duplicates
    boolean existsByCollection_IdAndLocation_Id(UUID collectionId, UUID locationId);

    // 🔥 Delete item
    void deleteByCollection_IdAndLocation_Id(UUID collectionId, UUID locationId);
}