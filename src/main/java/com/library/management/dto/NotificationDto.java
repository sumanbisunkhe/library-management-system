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
public class NotificationDto {

    private Long id;

    @NotNull(message = "User ID must not be null.")
    @Positive(message = "User ID must be a positive number.")
    private Long userId;

    @NotBlank(message = "Message must not be empty.")
    @Size(max = 255, message = "Message must not exceed 255 characters.")
    private String message;

    private LocalDateTime createdAt;

    private Boolean isRead;
}

