package com.karatesan.WebAppApi.services;


import com.karatesan.WebAppApi.dto.BlogPostCreationRequestDto;
import com.karatesan.WebAppApi.dto.BlogPostClientDataDto;
import com.karatesan.WebAppApi.exception.ResourceNotFoundException;
import com.karatesan.WebAppApi.exception.UserNotFoundException;
import com.karatesan.WebAppApi.model.BlogPost;
import com.karatesan.WebAppApi.model.security.BlogUser;
import com.karatesan.WebAppApi.repositories.BlogPostRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final UserService userService;


    public BlogPostClientDataDto createBlogPost(BlogPostCreationRequestDto data)
    {
        BlogUser user = userService.findUserByEmail(data.authorEmail()).orElseThrow(()->new UserNotFoundException());
        BlogPost blogPost = new BlogPost(user,data.content(), data.title());
        BlogPost post = blogPostRepository.save(blogPost);
        return BlogPostClientDataDto.ofBlogPost(post);
    }

    public BlogPostClientDataDto getBlogPostById(@NonNull Long id) {

        BlogPost post = blogPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog post with id: " + id + " not found."));
        return BlogPostClientDataDto.ofBlogPost(post);
    }
}
