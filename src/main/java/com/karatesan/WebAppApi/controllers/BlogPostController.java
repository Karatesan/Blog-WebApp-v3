package com.karatesan.WebAppApi.controllers;

import com.karatesan.WebAppApi.model.BlogPost;
import com.karatesan.WebAppApi.model.security.BlogUser;
import com.karatesan.WebAppApi.model.security.role.Privilege;
import com.karatesan.WebAppApi.model.security.role.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@RequestMapping("blogpost")
public class BlogPostController {

    @GetMapping
    public BlogPost getBlogPost() {
        Privilege privilege = new Privilege(1L, "READ");
        Role role = new Role(1L, "USER", Arrays.asList(privilege));
        BlogUser user = new BlogUser(1L, "Maciek", "G", "test@gmail.com", "password", Arrays.asList(role), null);
        return new BlogPost(1L, "Tytul", "Content", LocalDateTime.now(), null, user, null, 10);
    }

}
