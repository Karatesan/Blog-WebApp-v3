package com.karatesan.WebAppApi.utility;


import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

@Component
public class RefreshTokenGenerator {

    private static final String ALGORITHM = "SHA256";


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
