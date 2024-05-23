package com.karatesan.WebAppApi.model.security.jwt;

import com.karatesan.WebAppApi.model.security.BlogUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtils {

    private final String JWT_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ54YUn4";
    private final long ACCESS_TOKEN_VALIDITY = 60*60*1000;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    public JwtUtils() {
    }

//    public String generateToken(BlogUser user){
//
//        SecretKey secretKey = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
//
//        String jwt = Jwts.builder()
//                .issuer("Blog")
//                .subject("JWTToken")
//                .claim("username",)
//
//
//    }
}
