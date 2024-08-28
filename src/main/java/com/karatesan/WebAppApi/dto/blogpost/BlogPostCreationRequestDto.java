package com.karatesan.WebAppApi.dto.blogpost;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record BlogPostCreationRequestDto(
        @NotBlank(message = "Title must not be empty")
        @Size(max = 100, message = "Too long title")
        String title,
        @NotBlank(message = "Content must not be empty")
        @Size(max = 20000, message = "Content is too long mfucker!!")
        String content,
        List<ImageUploadDto> images
        )
{}
