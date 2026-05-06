package com.HiddenGems.backend.service;

import com.HiddenGems.backend.entity.Collection;
import com.HiddenGems.backend.repository.CollectionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository repo;

    // ✅ Create collection
    public Collection create(Collection c) {
        return repo.save(c);
    }

    // ✅ Get all collections for a user
    public List<Collection> getUserCollections(UUID userId) {
        return repo.findByUserId(userId);
    }

    // ✅ Get one collection
    public Collection getById(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection not found"));
    }

    // 🔥 Update collection (with ownership check)
    public Collection update(UUID id, UUID userId, Collection updated) {
        Collection existing = getById(id);

        if (!existing.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        existing.setName(updated.getName());
        return repo.save(existing);
    }

    // 🔥 Delete collection (with ownership check)
    public void delete(UUID id, UUID userId) {
        Collection existing = getById(id);

        if (!existing.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        repo.delete(existing);
    }
}