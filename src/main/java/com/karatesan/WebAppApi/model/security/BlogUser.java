package com.karatesan.WebAppApi.model.security;

import com.karatesan.WebAppApi.model.BlogPost;
import com.karatesan.WebAppApi.model.security.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogUser {
    private long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    //author,reader,admin?
    private Collection<Role> roles;
    private List<BlogPost> postsFromUser;

}
