package com.karatesan.WebAppApi.controllers;

import com.karatesan.WebAppApi.config.PublicEndpoint;
import com.karatesan.WebAppApi.services.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
public class BlogPostController {

    private final EmailService emailService;

    @GetMapping("/blogpost")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public String getBlogPost(Principal principal) throws MessagingException {

        return "DUPA ";
    }
}
