package com.karatesan.WebAppApi.services;

import com.karatesan.WebAppApi.dto.blogpost.BlogPostCreationRequestDto;
import com.karatesan.WebAppApi.model.BlogPost;
import com.karatesan.WebAppApi.model.security.BlogUser;
import com.karatesan.WebAppApi.repositories.BlogPostRepository;
import com.karatesan.WebAppApi.services.interfaces.BlogPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BlogPostServiceImplTest {

    @Mock
    private BlogPostRepository blogPostRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private BlogPostService blogPostService;
    private BlogPost blogPost;
    private BlogPostCreationRequestDto requestDto;

    @BeforeEach
    void setup(){
        blogPost = new BlogPost(new BlogUser(),"Content","title");
        requestDto = new BlogPostCreationRequestDto("title", "content",)
    }



    @Test
    public void testCreateBlogPost_throwsException_whenAuthorNotFoundInDatabase(){

        when(userService.findUserByEmail(anyString())).thenReturn(Optional.empty());

        blogPostService.createBlogPost()

    }

}