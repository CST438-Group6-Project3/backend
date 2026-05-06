package com.HiddenGems.backend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.HiddenGems.backend.entity.Collection;
import com.HiddenGems.backend.service.CollectionService;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    // ✅ Create collection
    @PostMapping
    public Collection createCollection(@RequestBody Collection collection) {
        return collectionService.create(collection);
    }

    // ✅ Get user's collections
    @GetMapping("/user/{userId}")
    public List<Collection> getUserCollections(@PathVariable UUID userId) {
        return collectionService.getUserCollections(userId);
    }

    // 🔥 Update collection
    @PutMapping("/{id}")
    public Collection updateCollection(@PathVariable UUID id,
                                       @RequestParam UUID userId,
                                       @RequestBody Collection updated) {
        return collectionService.update(id, userId, updated);
    }

    // 🔥 Delete collection
    @DeleteMapping("/{id}")
    public void deleteCollection(@PathVariable UUID id,
                                 @RequestParam UUID userId) {
        collectionService.delete(id, userId);
    }
}