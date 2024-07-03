package com.karatesan.WebAppApi.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.karatesan.WebAppApi.model.security.role.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailDto {
    private String name;
    private String lastName;
    private String email;
    private String userStatus;
    private LocalDateTime createdAt;
    private Collection<Role> roles;
}
