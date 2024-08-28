package com.karatesan.WebAppApi.dto.blogpost;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import lombok.NonNull;

public record CommentCreationRequestDto(
        @NonNull
        Long blogPostId,
        @NonNull
        @Max(value = 1000)
        String commentContent,
        @Nullable
        Long parentCommentId,
        @Nullable
        Long respondToId ) {
}
