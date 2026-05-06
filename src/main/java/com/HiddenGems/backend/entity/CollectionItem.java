package com.HiddenGems.backend.entity;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "collection_items")
public class CollectionItem {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "collection_id", nullable = false)
    private Collection collection;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    // getters/setters
    public UUID getId() {
        return id;
    }
    public Collection getCollection() {
        return collection;
    }
    public Location getLocation() {
        return location;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public void setCollection(Collection collection) {
        this.collection = collection;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
}