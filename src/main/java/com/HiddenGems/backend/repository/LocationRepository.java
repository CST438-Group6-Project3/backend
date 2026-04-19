package com.HiddenGems.backend.repository;

import com.HiddenGems.backend.entity.Location;
import com.HiddenGems.backend.entity.Location.Category;
import com.HiddenGems.backend.entity.Location.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {

    List<Location> findByStatus(Status status);

    List<Location> findByCategory(Category category);

    List<Location> findByCategoryAndStatus(Category category, Status status);

    List<Location> findByCreatedById(UUID createdById);
}