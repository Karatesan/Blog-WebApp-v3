package com.karatesan.WebAppApi.dto.blogpost;

import com.karatesan.WebAppApi.dto.user.BlogUserMinimalDataDto;
import com.karatesan.WebAppApi.model.BlogPost;

import java.time.LocalDateTime;

public record BlogPostClientDataDto(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime lastUpdate,
        BlogUserMinimalDataDto author,
        int rating) {

    public static BlogPostClientDataDto ofBlogPost(BlogPost post){
        return new BlogPostClientDataDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                BlogUserMinimalDataDto.ofBlogUser(post.getAuthor()),
                post.getRating());
    }
}
