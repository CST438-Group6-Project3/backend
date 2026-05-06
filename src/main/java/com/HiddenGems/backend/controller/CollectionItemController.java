package com.HiddenGems.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import com.HiddenGems.backend.entity.Location;
import com.HiddenGems.backend.service.CollectionItemService;

@RestController
@RequestMapping("/api/collections")
public class CollectionItemController {

    @Autowired
    private CollectionItemService service;

    @PostMapping("/{collectionId}/items")
    public void addItem(@PathVariable Long collectionId,
                        @RequestBody AddItemRequest request) {
        service.addItem(collectionId, request.locationId);
    }

    @GetMapping("/{collectionId}/items")
    public List<Location> getItems(@PathVariable Long collectionId) {
        return service.getLocationsByCollection(collectionId);
    }

    @DeleteMapping("/{collectionId}/items/{locationId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long collectionId,
                                           @PathVariable Long locationId) {
        service.removeItem(collectionId, locationId);
        return ResponseEntity.noContent().build();
    }
}