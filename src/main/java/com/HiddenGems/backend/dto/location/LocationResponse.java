package com.HiddenGems.backend.dto.location;

import com.HiddenGems.backend.entity.Location;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class LocationResponse {

    private UUID id;
    private String name;
    private String description;
    private Location.Category category;
    private String[] tags;
    private Double lat;
    private Double lng;
    private UUID createdById;
    private Location.Status status;
    private BigDecimal avgRating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String[] imageUrls;

    public LocationResponse() {
    }

   public LocationResponse(Location location) {
    this.id = location.getId();
    this.name = location.getName();
    this.description = location.getDescription();
    this.category = location.getCategory();
    this.tags = location.getTags() != null ? location.getTags() : new String[0];
    this.imageUrls = location.getImageUrls() != null ? location.getImageUrls() : new String[0];

    this.lat = location.getLat();
    this.lng = location.getLng();
    if (location.getCreatedBy() != null) {
        this.createdById = location.getCreatedBy().getId();
    } else {
        this.createdById = null;
    }

    this.status = location.getStatus();
    this.avgRating = location.getAvgRating();
    this.createdAt = location.getCreatedAt();
    this.updatedAt = location.getUpdatedAt();
}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location.Category getCategory() {
        return category;
    }

    public void setCategory(Location.Category category) {
        this.category = category;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public UUID getCreatedById() {
        return createdById;
    }

    public void setCreatedById(UUID createdById) {
        this.createdById = createdById;
    }

    public Location.Status getStatus() {
        return status;
    }

    public void setStatus(Location.Status status) {
        this.status = status;
    }

    public BigDecimal getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(BigDecimal avgRating) {
        this.avgRating = avgRating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String[] getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }
}