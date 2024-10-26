package com.library.management.service.impl;

import com.library.management.dto.ReviewDto;
import com.library.management.entities.Book;
import com.library.management.entities.Review;
import com.library.management.entities.User;
import com.library.management.repo.BookRepo;
import com.library.management.repo.ReviewRepo;
import com.library.management.repo.UserRepo;
import com.library.management.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {
        Review review = modelMapper.map(reviewDto, Review.class);

        User user = userRepo.findById(reviewDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepo.findById(reviewDto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        review.setUser(user);
        review.setBook(book);
        review.setCreatedAt(LocalDateTime.now());

        review = reviewRepo.save(review);
        return modelMapper.map(review, ReviewDto.class);
    }

    @Override
    public ReviewDto getReviewById(Long id) {
        Review review = reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return modelMapper.map(review, ReviewDto.class);
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        return reviewRepo.findAll().stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> getReviewsByBookId(Long bookId) {
        return reviewRepo.findByBookId(bookId).stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> getReviewsByUserId(Long userId) {
        return reviewRepo.findByUserId(userId).stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        // Retrieve existing review
        Review existingReview = reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // Update fields with new values
        existingReview.setReviewText(reviewDto.getReviewText());
        existingReview.setRating(reviewDto.getRating());

        // Optionally update the book/user if needed (commented out since it's generally not updated)
        // User user = userRepo.findById(reviewDto.getUserId())
        //         .orElseThrow(() -> new RuntimeException("User not found"));
        // existingReview.setUser(user);
        // Book book = bookRepo.findById(reviewDto.getBookId())
        //         .orElseThrow(() -> new RuntimeException("Book not found"));
        // existingReview.setBook(book);

        // Save the updated review
        existingReview = reviewRepo.save(existingReview);

        // Return the updated review as DTO
        return modelMapper.map(existingReview, ReviewDto.class);
    }

    @Override
    public void deleteReview(Long id) {
        Optional<Review> reviewOptional = reviewRepo.findById(id);
        if (reviewOptional.isPresent()) {
            reviewRepo.delete(reviewOptional.get());
        } else {
            throw new RuntimeException("Review not found");
        }
    }
}
