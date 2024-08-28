package com.karatesan.WebAppApi.services;

import com.karatesan.WebAppApi.dto.blogpost.CommentClientDataDto;
import com.karatesan.WebAppApi.dto.blogpost.CommentCreationRequestDto;
import com.karatesan.WebAppApi.model.BlogPost;
import com.karatesan.WebAppApi.model.Comment;
import com.karatesan.WebAppApi.model.security.BlogUser;
import com.karatesan.WebAppApi.repositories.BlogPostRepository;
import com.karatesan.WebAppApi.repositories.BlogUserRepository;
import com.karatesan.WebAppApi.repositories.CommentRepository;
import com.karatesan.WebAppApi.services.interfaces.BlogPostService;
import com.karatesan.WebAppApi.services.interfaces.CommentService;
import com.karatesan.WebAppApi.utility.AuthenticatedUserIdProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BlogPostRepository blogPostRepository;
    private final BlogUserRepository blogUserRepository;
    private final AuthenticatedUserIdProvider authenticatedUserIdProvider;
    private final BlogPostService blogPostService;
    private final UserService userService;

    @Override
    @Transactional
    public CommentClientDataDto addComment(CommentCreationRequestDto comment) {

        BlogPost post = blogPostService.findBlogPostById(comment.blogPostId());
        BlogUser author = userService.findAuthenticatedUser();
        Comment parentComment = findCommentById(comment.parentCommentId());
        Comment respondToComment = findCommentById(comment.respondToId());

        Comment newComment = new Comment(post,author,comment.commentContent(),parentComment,respondToComment);
        post.addComment(newComment);
        blogPostRepository.save(post);
        return CommentClientDataDto.ofComment(newComment);
    }

    public Comment findCommentById(Long commentId) {
        return commentId == null ? null : commentRepository.findById(commentId).orElse(null);
    }

}
