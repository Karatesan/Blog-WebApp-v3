package com.karatesan.WebAppApi.model.security;

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


@AllArgsConstructor
@NoArgsConstructor
@Data
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
    //author,reader,admin?
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
    private LocalDateTime createdAt;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roles_string = roles.stream()
                .map(r->r.getName()).collect(Collectors.joining(" "));
        String privileges_string = roles.stream()
                .map(r -> r.getPrivileges().stream()
                        .map(Privilege::getName).collect(Collectors.joining(" "))).collect(Collectors.joining(" "));
        String authorities = String.join(" ",roles_string, privileges_string);

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
