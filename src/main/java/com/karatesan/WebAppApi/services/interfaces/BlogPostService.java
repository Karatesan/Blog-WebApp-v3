package com.karatesan.WebAppApi.services.interfaces;

import com.karatesan.WebAppApi.dto.blogpost.BlogPostClientDataDto;
import com.karatesan.WebAppApi.dto.blogpost.BlogPostCreationRequestDto;
import com.karatesan.WebAppApi.model.BlogPost;

public interface BlogPostService {

    BlogPostClientDataDto createBlogPost(BlogPostCreationRequestDto data);
    BlogPostClientDataDto getBlogPostDataById(Long id);
    BlogPost findBlogPostById(Long id);

}
