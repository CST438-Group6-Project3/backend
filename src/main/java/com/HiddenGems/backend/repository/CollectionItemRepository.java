package com.HiddenGems.backend.repository;

import com.HiddenGems.backend.entity.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CollectionItemRepository extends JpaRepository<CollectionItem, Long> {

    List<CollectionItem> findByCollectionId(Long collectionId);

    boolean existsByCollectionIdAndLocationId(Long collectionId, UUID locationId);

    void deleteByCollectionIdAndLocationId(Long collectionId, UUID locationId);
}