package com.HiddenGems.backend.repository;

import com.HiddenGems.backend.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CollectionRepository extends JpaRepository<Collection, UUID> {

    List<Collection> findByUserId(UUID userId);
}