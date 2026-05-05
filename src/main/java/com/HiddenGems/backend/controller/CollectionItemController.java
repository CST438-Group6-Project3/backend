package com.HiddenGems.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.HiddenGems.backend.service.CollectionItemService;

@RestController
@RequestMapping("/api/collections")
public class CollectionItemController {

    @Autowired
    private CollectionItemService service;

    // Add location to collection
    @PostMapping("/{collectionId}/items")
    public void addItem(@PathVariable Long collectionId,
                        @RequestParam Long locationId) {
        service.addItem(collectionId, locationId);
    }

    // Remove location
    @DeleteMapping("/{collectionId}/items/{locationId}")
    public void removeItem(@PathVariable Long collectionId,
                           @PathVariable Long locationId) {
        service.removeItem(collectionId, locationId);
    }
}