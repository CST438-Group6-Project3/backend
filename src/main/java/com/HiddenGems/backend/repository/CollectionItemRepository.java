package com.HiddenGems.backend.repository;

import com.HiddenGems.backend.entity.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CollectionItemRepository extends JpaRepository<CollectionItem, Long> {

    List<CollectionItem> findByCollectionId(Long collectionId);

    void deleteByCollectionIdAndLocationId(Long collectionId, Long locationId);
}