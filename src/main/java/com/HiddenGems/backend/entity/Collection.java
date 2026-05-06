package com.HiddenGems.backend.entity;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "collections")
public class Collection {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID userId;

    private String name;

    // getters/setters

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }
}