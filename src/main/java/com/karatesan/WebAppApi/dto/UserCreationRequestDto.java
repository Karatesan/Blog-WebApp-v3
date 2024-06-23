package com.karatesan.WebAppApi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationRequestDto {

    @NotBlank(message = "first-name must not be empty")
    private String name;
    @NotBlank(message = "last-name must not be empty")
    private String lastName;
    @NotBlank(message = "email-id must not be empty")
    @Email(message = "email must be of valid format")
    private String email;
    @NotBlank(message = "password must not be empty")
    private String password;
    @NotBlank(message = "confirm password must not be empty")
    private String confirmPassword;
}
