package com.karatesan.WebAppApi.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {

    @NotBlank(message = "email must not be empty")
    @Email(message = "email must be of valid format")
    private String email;

    @NotBlank(message = "password must not be empty")
    private String password;
}
