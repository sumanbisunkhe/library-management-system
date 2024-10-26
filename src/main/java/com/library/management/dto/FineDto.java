package com.library.management.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FineDto {

    private Long id;

    @NotNull(message = "Borrow ID must not be null.")
    @Positive(message = "Borrow ID must be a positive number.")
    private Long borrowId;

    @NotNull(message = "Amount must not be null.")
    @Positive(message = "Amount must be a positive number.")
    private Double amount;

    @NotNull(message = "Due date must not be null.")
    @FutureOrPresent(message = "Due date must be in the present or future.")
    private LocalDateTime dueDate;

    private Boolean paid;

    private LocalDateTime createdAt;
}
