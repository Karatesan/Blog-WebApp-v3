package com.karatesan.WebAppApi.model.security;

import com.karatesan.WebAppApi.model.security.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "blog_users")
public class BlogUser {
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
    //author,reader,admin?
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
    private LocalDateTime createdAt;

}
