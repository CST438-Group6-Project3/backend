package com.HiddenGems.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.HiddenGems.backend.entity.Collection;
import com.HiddenGems.backend.service.CollectionService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @PostMapping
    public Collection createCollection(@RequestBody Collection collection) {
        return collectionService.create(collection);
    }

    @GetMapping
    public List<Collection> getUserCollections(@RequestParam Long userId) {
        return collectionService.getUserCollections(userId);
    }

    @PutMapping("/{id}")
    public Collection updateCollection(@PathVariable Long id,
                                       @RequestBody Collection updated) {
        return collectionService.update(id, updated);
    }

    @DeleteMapping("/{id}")
    public void deleteCollection(@PathVariable Long id) {
        collectionService.delete(id);
    }
}