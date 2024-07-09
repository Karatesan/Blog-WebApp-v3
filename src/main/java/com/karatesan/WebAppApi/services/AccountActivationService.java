package com.karatesan.WebAppApi.services;


import com.karatesan.WebAppApi.model.security.BlogUser;
import com.karatesan.WebAppApi.ulilityClassess.Token;
import com.karatesan.WebAppApi.utility.CacheManager;
import com.karatesan.WebAppApi.utility.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountActivationService {

    //TODO resen email

    private final TokenGenerator tokenGenerator;
    private final EmailService emailService;
    private final CacheManager cacheManager;

    void sendActivationRequest(BlogUser user){

        Token activationToken = tokenGenerator.createActivationToken();
        cacheManager.save(activationToken,user.getId());
        emailService.sendVerificationEmail(user.getName(),user.getEmail(),activationToken.token());
    }



}
