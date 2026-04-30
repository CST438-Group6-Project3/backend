package com.HiddenGems.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "collection_items")
public class CollectionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long collectionId;
    private Long locationId;

    public Long getId() {
        return id;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}