package com.karatesan.WebAppApi.controllers;

import com.karatesan.WebAppApi.config.PublicEndpoint;
import com.karatesan.WebAppApi.dto.BlogPostCreationRequestDto;
import com.karatesan.WebAppApi.dto.BlogPostClientDataDto;
import com.karatesan.WebAppApi.services.BlogPostService;
import com.karatesan.WebAppApi.services.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/blogpost")
public class BlogPostController {

    private final EmailService emailService;
    private final BlogPostService blogPostService;

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    @PublicEndpoint
    public BlogPostClientDataDto getBlogPost(@PathVariable(name = "id") Long id) {
        return blogPostService.getBlogPostById(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE")
    public ResponseEntity<BlogPostClientDataDto> createBlogPost(@RequestBody BlogPostCreationRequestDto data){

        BlogPostClientDataDto post = blogPostService.createBlogPost(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    /*
    @DeleteMapping("/delete/{id}") @PathVariable
    @DeleteMapping("/delete?id={id}") @RequestParam
     */
    @DeleteMapping("/delete/{id}")
    public String deleteBlogPost(@PathVariable long id){
//        System.out.println("w delecie");
//        Optional<BlogPost> byId = blogPostRepository.findById(id);
//        if(byId.isPresent()){
//            Optional<BlogUser> byEmail = blogUserRepository.findByEmail(byId.get().getAuthor().getEmail());
//            BlogUser user = byEmail.get();
//            user.getBlogPosts().forEach(b->b.setAuthor(null));
//            commentRepository.unsetCommentAuthor(user);
//            blogUserRepository.save(user);
//            blogUserRepository.delete(user);
//            //blogPostRepository.delete(byId.get());
//        }
        return "dupa";

    }
}
