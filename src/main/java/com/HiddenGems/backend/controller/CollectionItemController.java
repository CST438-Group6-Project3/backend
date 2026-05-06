package com.HiddenGems.backend.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    // 🔥 Remove location from collection
    @DeleteMapping("/{collectionId}/locations/{locationId}")
    public void removeItem(@PathVariable UUID collectionId,
                           @PathVariable UUID locationId) {
        service.removeItem(collectionId, locationId);
    }
}