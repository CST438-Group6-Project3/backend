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

    public List<Collection> getUserCollections(Long userId) {
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