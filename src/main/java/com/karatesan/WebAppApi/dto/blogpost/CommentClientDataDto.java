package com.karatesan.WebAppApi.dto.blogpost;

import com.karatesan.WebAppApi.dto.user.BlogUserMinimalDataDto;
import com.karatesan.WebAppApi.model.Comment;

import java.time.LocalDateTime;

public record CommentClientDataDto(
        Long id,
        BlogUserMinimalDataDto author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String commentContnent,
        int rating,
        BlogUserMinimalDataDto respondTo) {

    public static CommentClientDataDto ofComment(Comment comment){
        return new CommentClientDataDto(
                comment.getId(),
                BlogUserMinimalDataDto.ofBlogUser(comment.getCommentAuthor()),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getCommentContent(),
                comment.getRating(),
                BlogUserMinimalDataDto.ofBlogUser(comment.getRespondTo().getCommentAuthor()));
    }
}
