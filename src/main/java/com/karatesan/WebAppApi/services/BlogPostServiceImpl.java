package com.karatesan.WebAppApi.services;


import com.karatesan.WebAppApi.dto.blogpost.BlogPostCreationRequestDto;
import com.karatesan.WebAppApi.dto.blogpost.BlogPostClientDataDto;
import com.karatesan.WebAppApi.exception.ResourceNotFoundException;
import com.karatesan.WebAppApi.model.BlogPost;
import com.karatesan.WebAppApi.model.security.BlogUser;
import com.karatesan.WebAppApi.repositories.BlogPostRepository;
import com.karatesan.WebAppApi.services.interfaces.BlogPostService;
import com.karatesan.WebAppApi.services.interfaces.ImageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final UserService userService;
    private final ImageService imageService;


    public BlogPostClientDataDto createBlogPost(BlogPostCreationRequestDto data)
    {
        BlogUser user = userService.findAuthenticatedUser();
        BlogPost blogPost = new BlogPost(user,data.content(), data.title());
        BlogPost post = blogPostRepository.save(blogPost);
        return BlogPostClientDataDto.ofBlogPost(post);
    }

    public BlogPostClientDataDto getBlogPostDataById(@NonNull Long id) {

        BlogPost post = blogPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog post with id: " + id + " not found."));
        return BlogPostClientDataDto.ofBlogPost(post);
    }

    @Override
    public BlogPost findBlogPostById(Long id) {
        return blogPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found."));
    }


    //Comments


    //TODO delete, update
}
