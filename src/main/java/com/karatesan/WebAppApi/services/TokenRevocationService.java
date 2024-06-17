package com.karatesan.WebAppApi.services;


import com.karatesan.WebAppApi.utility.CacheManager;
import com.karatesan.WebAppApi.utility.JwtUtility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenRevocationService {

    private final JwtUtility jwtUtility;
    private final CacheManager cacheManager;
    private final HttpServletRequest httpServletRequest;

    public void revoke(){
        String authorization = Optional.ofNullable(httpServletRequest.getHeader("Authorization")).orElseThrow(IllegalStateException::new);
        String jti = jwtUtility.getJti(authorization);
        Duration ttl = jwtUtility.getTimeUntilExpiration(authorization);
        cacheManager.save(jti,ttl);
    }

    public boolean isRevoked(@NonNull final String authHeader){
        final String jti = jwtUtility.getJti(authHeader);
        return cacheManager.isPresent(jti);
    }



}
