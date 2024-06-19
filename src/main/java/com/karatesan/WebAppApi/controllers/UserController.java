package com.karatesan.WebAppApi.controllers;


import com.karatesan.WebAppApi.config.PublicEndpoint;
import com.karatesan.WebAppApi.dto.ResetPasswordRequestDto;
import com.karatesan.WebAppApi.dto.UserCreationRequestDto;
import com.karatesan.WebAppApi.dto.UserDetailDto;
import com.karatesan.WebAppApi.services.UserService;
import com.karatesan.WebAppApi.utility.AuthenticatedUserIdProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;
    private final AuthenticatedUserIdProvider authenticatedUserIdProvider;

    @PublicEndpoint
    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody final UserCreationRequestDto userCreationRequest){
        userService.create((userCreationRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserDetailDto>retrieveUser(){
        final Long userId = authenticatedUserIdProvider.getUserId();
        final UserDetailDto userDetail = userService.getUserById(userId);
        return ResponseEntity.ok(userDetail);
    }

    @PublicEndpoint
    @PutMapping(value = "/reset-password")
    public ResponseEntity<HttpStatus>resetPassword(@Valid @RequestBody final ResetPasswordRequestDto details){
        userService.resetPassword(details);
        return  ResponseEntity.status(HttpStatus.OK).build();
    }
}
