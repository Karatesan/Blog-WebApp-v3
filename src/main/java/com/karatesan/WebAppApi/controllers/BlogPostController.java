package com.karatesan.WebAppApi.controllers;

import com.karatesan.WebAppApi.dto.blogpost.BlogPostCreationRequestDto;
import com.karatesan.WebAppApi.dto.blogpost.BlogPostClientDataDto;
import com.karatesan.WebAppApi.services.interfaces.BlogPostService;
import com.karatesan.WebAppApi.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController

@RequestMapping(value = "/blogpost")
public class BlogPostController {

    private final EmailService emailService;
    private final BlogPostService blogPostService;

    public BlogPostController(EmailService emailService, BlogPostService blogPostService) {
        this.emailService = emailService;
        this.blogPostService = blogPostService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    //@PublicEndpoint
    public BlogPostClientDataDto getBlogPost(@PathVariable(name = "id") Long id) {
        return blogPostService.getBlogPostDataById(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<BlogPostClientDataDto> createBlogPost(@RequestBody @Valid BlogPostCreationRequestDto data){
        System.out.println("dsadas");
        BlogPostClientDataDto post = blogPostService.createBlogPost(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    /*
    @DeleteMapping("/delete/{id}") @PathVariable
    @DeleteMapping("/delete?id={id}") @RequestParam
     */

}
