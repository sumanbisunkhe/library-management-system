package com.library.management.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {

    private Long id;

    @NotNull(message = "User ID must not be null.")
    @Positive(message = "User ID must be a positive number.")
    private Long userId;

    @NotNull(message = "Book ID must not be null.")
    @Positive(message = "Book ID must be a positive number.")
    private Long bookId;

    @NotNull(message = "Reserved At timestamp must not be null.")
    @FutureOrPresent(message = "Reserved At must be in the present or future.")
    private LocalDateTime reservedAt;

    @NotNull(message = "Expiration At timestamp must not be null.")
    @FutureOrPresent(message = "Expiration At must be in the present or future.")
    private LocalDateTime expirationAt;

    private Boolean isConfirmed;
}
