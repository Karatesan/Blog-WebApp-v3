package com.karatesan.WebAppApi.services.interfaces;

import com.karatesan.WebAppApi.dto.blogpost.CommentClientDataDto;
import com.karatesan.WebAppApi.dto.blogpost.CommentCreationRequestDto;
import com.karatesan.WebAppApi.model.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    CommentClientDataDto addComment(CommentCreationRequestDto comment);
    Comment findCommentById(Long commentId);



}
