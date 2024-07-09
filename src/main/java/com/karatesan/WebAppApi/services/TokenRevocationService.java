package com.karatesan.WebAppApi.services;


import com.karatesan.WebAppApi.exception.TokenVerificationException;
import com.karatesan.WebAppApi.utility.CacheManager;
import com.karatesan.WebAppApi.utility.JwtUtility;
import com.karatesan.WebAppApi.utility.RefreshTokenHeaderProvider;
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
    private final RefreshTokenHeaderProvider refreshTokenHeaderProvider;

    public void revokeAccessToken(){
        String authorization = Optional.ofNullable(httpServletRequest.getHeader("Authorization")).orElseThrow(IllegalStateException::new);
        String jti = jwtUtility.getJti(authorization);
        Duration ttl = jwtUtility.getTimeUntilExpiration(authorization);
        cacheManager.save(jti,ttl);
    }

    public void revokeRefreshToken(){
        String refreshToken = refreshTokenHeaderProvider.getRefreshToken().orElseThrow(TokenVerificationException::new);
        cacheManager.delete(refreshToken);
    }

    public void invalidateTokensForUser(){
        revokeAccessToken();
        revokeRefreshToken();
    }

    public boolean isRevoked(@NonNull final String authHeader){
        final String jti = jwtUtility.getJti(authHeader);
        return cacheManager.isPresent(jti);
    }
//Implement token revocation (e.g., if a user logs out or changes their password).


}
