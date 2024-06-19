package com.karatesan.WebAppApi.services;

import com.karatesan.WebAppApi.config.TokenConfigurationProperties;
import com.karatesan.WebAppApi.dto.TokenSuccessResponseDto;
import com.karatesan.WebAppApi.dto.UserLoginRequestDto;
import com.karatesan.WebAppApi.exception.InvalidCredentialsException;
import com.karatesan.WebAppApi.exception.TokenVerificationException;
import com.karatesan.WebAppApi.model.security.BlogUser;
import com.karatesan.WebAppApi.repositories.BlogUserRepository;
import com.karatesan.WebAppApi.utility.CacheManager;
import com.karatesan.WebAppApi.utility.JwtUtility;
import com.karatesan.WebAppApi.utility.RefreshTokenGenerator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(TokenConfigurationProperties.class)
public class AuthenticationService {

    private final JwtUtility jwtUtility;
    private final CacheManager cacheManager;
    private final BlogUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final CompromisedPasswordChecker compromisedPasswordChecker;
    private final TokenConfigurationProperties tokenConfigurationProperties;
    private final TokenRevocationService tokenRevocationService;


    public TokenSuccessResponseDto login(@NonNull final UserLoginRequestDto userLoginRequestDto){

        final BlogUser user = userRepository.findByEmail(userLoginRequestDto.getEmailId())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid login credentials provided."));

        final String encodedPassword = user.getPassword();
        final String plainTextPassword = userLoginRequestDto.getPassword();

        final boolean isCorrectPassword = passwordEncoder.matches(plainTextPassword,encodedPassword);
        if(!isCorrectPassword)
            throw new InvalidCredentialsException("Invalid login credentials provided.");

        final boolean isPasswordCompromised = compromisedPasswordChecker.check(plainTextPassword).isCompromised();
        if(isPasswordCompromised)
            throw new CompromisedPasswordException("Password has been compromised. Password reset required.");

        final String accessToken = jwtUtility.generateAccessToken(user);
        final String refreshToken = refreshTokenGenerator.generate();
        Integer validity = tokenConfigurationProperties.getRefreshToken().getValidity();
        cacheManager.save(refreshToken,user.getId(), Duration.ofMinutes(validity));

        return TokenSuccessResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(){
        tokenRevocationService.revokeAccessToken();
        tokenRevocationService.revokeRefreshToken();
    }

    public TokenSuccessResponseDto refreshToken(@NonNull final String refreshToken){
        final long userId = cacheManager.fetch(refreshToken, Long.class).orElseThrow(TokenVerificationException::new);
        final BlogUser user = userRepository.getReferenceById(userId);
        final String accessToken = jwtUtility.generateAccessToken(user);

        return TokenSuccessResponseDto.builder()
                .accessToken(accessToken)
                .build();
    }

}
