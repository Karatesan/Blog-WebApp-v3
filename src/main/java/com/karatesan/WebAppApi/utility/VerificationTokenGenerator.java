package com.karatesan.WebAppApi.utility;

import com.karatesan.WebAppApi.config.TokenConfigurationProperties;
import com.karatesan.WebAppApi.ulilityClassess.token;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(TokenConfigurationProperties.class)
public class VerificationTokenGenerator {

    private final TokenConfigurationProperties tokenConfigurationProperties;

        public token createVerificationToken(){
            String token = UUID.randomUUID().toString();
            Integer validity = tokenConfigurationProperties.getValidationToken().getValidity();
            return new token(token,validity);
        }

}
