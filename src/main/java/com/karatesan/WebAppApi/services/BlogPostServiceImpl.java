package com.karatesan.WebAppApi.services;


import com.karatesan.WebAppApi.dto.blogpost.BlogPostCreationRequestDto;
import com.karatesan.WebAppApi.dto.blogpost.BlogPostClientDataDto;
import com.karatesan.WebAppApi.dto.blogpost.ImageUploadDto;
import com.karatesan.WebAppApi.exception.ResourceNotFoundException;
import com.karatesan.WebAppApi.model.BlogPost;
import com.karatesan.WebAppApi.model.Image;
import com.karatesan.WebAppApi.model.security.BlogUser;
import com.karatesan.WebAppApi.repositories.BlogPostRepository;
import com.karatesan.WebAppApi.services.interfaces.BlogPostService;
import com.karatesan.WebAppApi.services.interfaces.ImageService;
import com.karatesan.WebAppApi.ulilityClassess.ImageLocationData;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final UserService userService;
    private final ImageService imageService;

    //TODO how to avoid saving blog post twice
    @Transactional
    public BlogPostClientDataDto createBlogPost(BlogPostCreationRequestDto data)
    {
        BlogUser user = userService.findAuthenticatedUser();

        BlogPost blogPost = new BlogPost(user,data.content(), data.title());
        BlogPost post = blogPostRepository.save(blogPost);

        for(int i=0;i<data.images().size();++i){
            ImageLocationData imageLocationData = imageService.saveImage(post.getId(), data.images().get(i));
            Image image = new Image(imageLocationData.directory(),imageLocationData.imageName(), post);
            post.addImage(image);
        }
        blogPostRepository.save(blogPost);
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
