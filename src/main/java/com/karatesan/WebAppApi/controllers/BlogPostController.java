package com.karatesan.WebAppApi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BlogPostController {

    @GetMapping("/blogpost")
    public String getBlogPost() {
        System.out.println("dasdasdas");
//        Privilege privilege = new Privilege(1L, "READ");
//        Role role = new Role(1L, "USER", Arrays.asList(privilege));
//        BlogUser user = new BlogUser(1L, "Maciek", "G", "test@gmail.com", "password", Arrays.asList(role), null);
//        return new BlogPost(1L, "Tytul", "Content", LocalDateTime.now(), null, user, null, 10);
        return "DUPA";
    }
}
