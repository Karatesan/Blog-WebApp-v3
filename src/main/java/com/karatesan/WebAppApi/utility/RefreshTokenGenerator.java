package com.karatesan.WebAppApi.utility;


import com.karatesan.WebAppApi.RefreshToken;
import com.karatesan.WebAppApi.config.TokenConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(TokenConfigurationProperties.class)
public class RefreshTokenGenerator {

    private static final String ALGORITHM = "SHA256";
    private final TokenConfigurationProperties tokenConfigurationProperties;


    public RefreshToken createToken(){
        final String token = generate();
        Integer validity = tokenConfigurationProperties.getRefreshToken().getValidity();
        return new RefreshToken(token,validity);
    }

    @SneakyThrows
    public String generate(){
        final var randomIdentifier = String.valueOf(UUID.randomUUID());
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
        final var hash = messageDigest.digest(randomIdentifier.getBytes(StandardCharsets.UTF_8));
        return convertBytesToString(hash);

    }

    private String convertBytesToString(byte[] hash) {
        final var hexStringBuilder = new StringBuilder();
        for(final byte currentByte : hash){
            final var hexValue = String.format("%02x", currentByte);
            hexStringBuilder.append(hexValue);
        }
        return hexStringBuilder.toString();
    }
}
