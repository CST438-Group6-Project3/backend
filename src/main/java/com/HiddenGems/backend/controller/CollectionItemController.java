package com.HiddenGems.backend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.HiddenGems.backend.entity.Location;
import com.HiddenGems.backend.service.CollectionItemService;

@RestController
@RequestMapping("/api/collections")
public class CollectionItemController {

    @Autowired
    private CollectionItemService service;

    // 🔥 Add location to collection
    @PostMapping("/{collectionId}/locations/{locationId}")
    public void addItem(@PathVariable UUID collectionId,
                        @PathVariable UUID locationId) {
        service.addItem(collectionId, locationId);
    }

    // 🔥 Remove location
    @DeleteMapping("/{collectionId}/locations/{locationId}")
    public void removeItem(@PathVariable UUID collectionId,
                           @PathVariable UUID locationId) {
        service.removeItem(collectionId, locationId);
    }

    // 🔥 Get all locations in a collection (album view)
    @GetMapping("/{collectionId}/locations")
    public List<Location> getItems(@PathVariable UUID collectionId) {
        return service.getItems(collectionId);
    }
}