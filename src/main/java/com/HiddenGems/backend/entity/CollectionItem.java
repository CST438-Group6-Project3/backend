package com.HiddenGems.backend.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(
    name = "collection_items",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"collectionId", "locationId"})
    }
)
public class CollectionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long collectionId;

    @Column(nullable = false)
    private UUID locationId;

    public Long getId() {
        return id;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }
}