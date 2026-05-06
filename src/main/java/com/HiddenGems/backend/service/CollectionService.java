package com.HiddenGems.backend.service;

import com.HiddenGems.backend.entity.Collection;
import com.HiddenGems.backend.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository repo;

    public Collection create(Collection c) {
        return repo.save(c);
    }

    // 🔥 MAIN METHOD YOU SHOULD USE
    public Collection getOrCreateDefaultCollection(String userId) {

        return repo.findByUserId(userId)
            .stream()
            .findFirst()
            .orElseGet(() -> {
                Collection c = new Collection();
                c.setUserId(userId);
                c.setName("My Collection");
                return repo.save(c);
            });
    }

    // optional (if you still want list)
    public List<Collection> getUserCollections(String userId) {
        return repo.findByUserId(userId);
    }

    public Collection update(Long id, Collection updated) {
        Collection existing = repo.findById(id).orElseThrow();
        existing.setName(updated.getName());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}