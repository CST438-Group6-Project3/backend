package com.HiddenGems.backend.controller;

import com.HiddenGems.backend.entity.Review;
import com.HiddenGems.backend.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Review review) {
        if (review.getUserId() == null || review.getLocationId() == null) {
            return ResponseEntity.badRequest().body("userId and locationId are required.");
        }

        if (review.getRating() == null || review.getRating() < 1 || review.getRating() > 5) {
            return ResponseEntity.badRequest().body("Rating must be between 1 and 5.");
        }

        if (reviewRepository.existsByUserIdAndLocationId(review.getUserId(), review.getLocationId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User has already reviewed this location.");
        }

        Review savedReview = reviewRepository.save(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<List<Review>> getReviewsByLocation(@PathVariable Long locationId) {
        List<Review> reviews = reviewRepository.findByLocationId(locationId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{locationId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long locationId) {
        Double averageRating = reviewRepository.getAverageRatingByLocationId(locationId);

        if (averageRating == null) {
            return ResponseEntity.ok(0.0);
        }

        double roundedAverage = Math.round(averageRating * 10.0) / 10.0;
        return ResponseEntity.ok(roundedAverage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        Optional<Review> review = reviewRepository.findById(id);

        if (review.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found.");
        }

        reviewRepository.deleteById(id);
        return ResponseEntity.ok("Review deleted.");
    }

    @PutMapping("/{id}/upvote")
    public ResponseEntity<?> upvoteReview(@PathVariable Long id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);

        if (reviewOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found.");
        }

        Review review = reviewOptional.get();
        review.setUpvotes(review.getUpvotes() + 1);
        reviewRepository.save(review);

        return ResponseEntity.ok(review);
    }

    @PutMapping("/{id}/downvote")
    public ResponseEntity<?> downvoteReview(@PathVariable Long id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);

        if (reviewOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found.");
        }

        Review review = reviewOptional.get();
        review.setDownvotes(review.getDownvotes() + 1);
        reviewRepository.save(review);

        return ResponseEntity.ok(review);
    }
}