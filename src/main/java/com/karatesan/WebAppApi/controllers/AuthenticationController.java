package com.karatesan.WebAppApi.controllers;

import com.karatesan.WebAppApi.config.PublicEndpoint;
import com.karatesan.WebAppApi.dto.TokenSuccessResponseDto;
import com.karatesan.WebAppApi.dto.UserLoginRequestDto;
import com.karatesan.WebAppApi.services.AuthenticationService;
import com.karatesan.WebAppApi.utility.TokenHeaderProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final TokenHeaderProvider tokenHeaderProvider;

    @PublicEndpoint
    @PostMapping(value = "/login")
    public ResponseEntity<TokenSuccessResponseDto>login(@Valid @RequestBody final UserLoginRequestDto loginRequest){
        System.out.println("test");
        TokenSuccessResponseDto response = authenticationService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PublicEndpoint
    @PutMapping(value = "/refresh")
    public ResponseEntity<TokenSuccessResponseDto>refreshToken(@RequestHeader(value = "X-Refresh-Token",required = false)String refreshToken){
        //String refreshToken = refreshTokenHeaderProvider.getRefreshToken().orElseThrow(TokenVerificationException::new);
        TokenSuccessResponseDto tokenResponse = authenticationService.refreshToken(refreshToken);
        return ResponseEntity.ok(tokenResponse);
    }
    @PublicEndpoint
    @PostMapping("/logout")
    public ResponseEntity<HttpStatus>logout(){
        authenticationService.logout();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
