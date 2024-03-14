package com.karatesan.WebAppApi.model.security.role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
@Entity
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}
