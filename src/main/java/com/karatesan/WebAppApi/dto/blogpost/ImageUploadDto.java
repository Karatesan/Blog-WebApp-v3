package com.karatesan.WebAppApi.dto.blogpost;

import org.springframework.web.multipart.MultipartFile;

public record ImageUploadDto(
        String location,
        MultipartFile image
) {
}
