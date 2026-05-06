package com.HiddenGems.backend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.HiddenGems.backend.entity.Collection;
import com.HiddenGems.backend.service.CollectionService;
import com.HiddenGems.backend.service.CollectionItemService;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private CollectionItemService collectionItemService;

    @PostMapping
    public Collection createCollection(@RequestBody Collection collection) {
        if (collection.getName() == null || collection.getName().isEmpty()) {
            throw new RuntimeException("Collection name required");
        }
        return collectionService.create(collection);
    }

    @GetMapping
    public List<Collection> getUserCollections(@RequestParam String userId) {
        return collectionService.getUserCollections(userId);
    }

    @PutMapping("/{id}")
    public Collection updateCollection(@PathVariable Long id,
                                       @RequestBody Collection updated) {
        return collectionService.update(id, updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
        collectionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ADD THIS
    @PostMapping("/save")
    public void saveToCollection(@RequestParam String userId,
                                 @RequestParam UUID locationId) {

        Collection collection = collectionService.getOrCreateDefaultCollection(userId);

        collectionItemService.addItem(collection.getId(), locationId);
    }
}