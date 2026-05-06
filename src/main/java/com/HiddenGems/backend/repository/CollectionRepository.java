package com.HiddenGems.backend.repository;

import com.HiddenGems.backend.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    List<Collection> findByUserId(String userId);
}