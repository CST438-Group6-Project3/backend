package com.HiddenGems.backend.repository;

import com.HiddenGems.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByLocationId(Long locationId);

    boolean existsByUserIdAndLocationId(Long userId, Long locationId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.locationId = :locationId")
    Double getAverageRatingByLocationId(Long locationId);
}