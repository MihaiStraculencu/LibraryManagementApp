package com.itschool.library_management.controller;

import com.itschool.library_management.dto.ReviewDto;
import com.itschool.library_management.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByBookId(@PathVariable Long bookId) {
        return ResponseEntity.ok(reviewService.getReviewsByBookId(bookId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        return reviewService.createReview(reviewDto)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        return reviewService.updateReview(id, reviewDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        return reviewService.deleteReview(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}