package com.karatesan.WebAppApi.controllers;

import com.karatesan.WebAppApi.config.PublicEndpoint;
import com.karatesan.WebAppApi.services.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
public class BlogPostController {

    private final EmailService emailService;

    @GetMapping("/blogpost")
    @PublicEndpoint
    public String getBlogPost(Principal principal) throws MessagingException {
        System.out.println("dasdasdas");
        emailService.sendEmailFromTemplate();
//        Privilege privilege = new Privilege(1L, "READ");
//        Role role = new Role(1L, "USER", Arrays.asList(privilege));
//        BlogUser user = new BlogUser(1L, "Maciek", "G", "test@gmail.com", "password", Arrays.asList(role), null);
//        return new BlogPost(1L, "Tytul", "Content", LocalDateTime.now(), null, user, null, 10);
        return "DUPA ";
    }
}
