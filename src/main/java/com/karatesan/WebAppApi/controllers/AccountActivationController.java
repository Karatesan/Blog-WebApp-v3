package com.karatesan.WebAppApi.controllers;
import com.karatesan.WebAppApi.config.PublicEndpoint;
import com.karatesan.WebAppApi.services.AccountActivationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activate")
public class AccountActivationController {

    private final AccountActivationService accountActivationService;

    @PublicEndpoint
    @GetMapping("/{token}")
    public ResponseEntity<HttpStatus> activateUser(@PathVariable String token) {

        accountActivationService.activateAccount(token);
        return ResponseEntity.ok().build();
    }

    //for logged in users, requestParam to jest ?email=costam
    @PublicEndpoint
    @PostMapping("/resend-verification-link")
    public ResponseEntity<HttpStatus> resendActivationLink(@RequestParam(required = false) String email) {

        if (email == null)
            accountActivationService.resendActivationLink();
        else
            accountActivationService.resendActivationLink(email);
        return ResponseEntity.ok().build();
    }
}
