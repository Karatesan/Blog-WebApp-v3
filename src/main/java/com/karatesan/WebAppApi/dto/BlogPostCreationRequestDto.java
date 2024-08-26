package com.karatesan.WebAppApi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public record BlogPostCreationRequestDto(
        @NotBlank(message = "Title must not be empty")
        @Max(value = 100, message = "Too long title")
        String title,
        @NotBlank(message = "Content must not be empty")
        @Max(value = 20000, message = "Content is too long mfucker!!")
        String content,
        @NotBlank(message = "Email must not be empty")
        @Email(message = "Wrong email format")
        String authorEmail) {
}
