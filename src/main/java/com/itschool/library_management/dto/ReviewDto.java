package com.itschool.library_management.dto;

import java.time.LocalDateTime;

public class ReviewDto {
    private Long id;
    private Integer rating;
    private String comment;
    private String reviewerName;
    private LocalDateTime createdAt;
    private Long bookId;

    // Constructors
    public ReviewDto() {
    }

    public ReviewDto(Long id, Integer rating, String comment, String reviewerName,
                     LocalDateTime createdAt, Long bookId) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.reviewerName = reviewerName;
        this.createdAt = createdAt;
        this.bookId = bookId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}