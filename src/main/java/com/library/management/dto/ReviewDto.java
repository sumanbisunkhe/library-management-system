package com.library.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long id;

    @NotNull(message = "User ID must not be null.")
    @Positive(message = "User ID must be a positive number.")
    private Long userId;

    @NotNull(message = "Book ID must not be null.")
    @Positive(message = "Book ID must be a positive number.")
    private Long bookId;

    @NotBlank(message = "Review text must not be empty.")
    @Size(max = 500, message = "Review text must not exceed 500 characters.")
    private String reviewText;

    @NotNull(message = "Rating must not be null.")
    @Positive(message = "Rating must be a positive number.")
    private Integer rating;

    private LocalDateTime createdAt;
}
