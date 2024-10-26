package com.library.management.service;

import com.library.management.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto);
    ReviewDto getReviewById(Long id);
    List<ReviewDto> getAllReviews();
    List<ReviewDto> getReviewsByBookId(Long bookId);
    List<ReviewDto> getReviewsByUserId(Long userId);
    ReviewDto updateReview(Long id, ReviewDto reviewDto);
    void deleteReview(Long id);
}
