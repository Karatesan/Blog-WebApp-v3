package com.karatesan.WebAppApi.model.security;


import com.karatesan.WebAppApi.model.BlogPost;
import com.karatesan.WebAppApi.model.Comment;
import com.karatesan.WebAppApi.model.security.role.Privilege;
import com.karatesan.WebAppApi.model.security.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;



@NoArgsConstructor
@Data
@ToString(exclude = "blogPosts")
@Entity
@Table(name = "blog_users")
public class BlogUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus userStatus;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;
    private LocalDateTime createdAt;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<BlogPost>blogPosts;
//    @OneToMany
//    private List<Comment> comments;


    public BlogUser(String name, String lastName, String email, String password, UserStatus userStatus) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userStatus = userStatus;
        this.blogPosts = new ArrayList<>();
        this.roles = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    public void addBlogPost(BlogPost post){
        blogPosts.add(post);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return userStatus.equals(UserStatus.DEACTIVATED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userStatus.equals(UserStatus.APPROVED);
    }
}
