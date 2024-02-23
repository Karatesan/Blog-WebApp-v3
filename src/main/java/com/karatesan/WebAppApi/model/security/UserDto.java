package com.karatesan.WebAppApi.model.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private String password;
    private String matchingPassword;
    private String email;
}
