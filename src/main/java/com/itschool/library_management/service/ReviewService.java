package com.itschool.library_management.service;

import com.itschool.library_management.dto.ReviewDto;
import com.itschool.library_management.entity.Book;
import com.itschool.library_management.entity.Review;
import com.itschool.library_management.repository.BookRepository;
import com.itschool.library_management.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ReviewDto> getReviewsByBookId(Long bookId) {
        return reviewRepository.findByBookId(bookId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<ReviewDto> getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(this::convertToDto);
    }

    public Optional<ReviewDto> createReview(ReviewDto reviewDto) {
        Review review = convertToEntity(reviewDto);
        if (review.getBook() == null) {
            return Optional.empty();
        }
        Review savedReview = reviewRepository.save(review);
        return Optional.of(convertToDto(savedReview));
    }

    public Optional<ReviewDto> updateReview(Long id, ReviewDto reviewDto) {
        if (!reviewRepository.existsById(id)) {
            return Optional.empty();
        }
        reviewDto.setId(id);
        Review review = convertToEntity(reviewDto);
        if (review.getBook() == null) {
            return Optional.empty();
        }
        Review updatedReview = reviewRepository.save(review);
        return Optional.of(convertToDto(updatedReview));
    }

    public boolean deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            return false;
        }
        reviewRepository.deleteById(id);
        return true;
    }

    private ReviewDto convertToDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setReviewerName(review.getReviewerName());
        dto.setCreatedAt(review.getCreatedAt());
        if (review.getBook() != null) {
            dto.setBookId(review.getBook().getId());
        }
        return dto;
    }

    private Review convertToEntity(ReviewDto dto) {
        Review review = new Review();
        review.setId(dto.getId());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setReviewerName(dto.getReviewerName());
        review.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : review.getCreatedAt());

        if (dto.getBookId() != null) {
            Book book = bookRepository.findById(dto.getBookId()).orElseGet(() -> null);
            review.setBook(book);
        }

        return review;
    }
}