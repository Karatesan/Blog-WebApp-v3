package com.karatesan.WebAppApi.model.security.role;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
@Data
@AllArgsConstructor
public class Role {

    private long id;
    private String name;
    private Collection<Privilege> privileges;
}
