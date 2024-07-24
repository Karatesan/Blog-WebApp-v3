package com.karatesan.WebAppApi.services;


import com.karatesan.WebAppApi.dto.TokenSuccessResponseDto;
import com.karatesan.WebAppApi.exception.TokenVerificationException;
import com.karatesan.WebAppApi.exception.UserNotFoundException;
import com.karatesan.WebAppApi.model.security.BlogUser;
import com.karatesan.WebAppApi.repositories.BlogUserRepository;
import com.karatesan.WebAppApi.utility.CacheManager;
import com.karatesan.WebAppApi.utility.JwtUtility;
import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@RequiredArgsConstructor
public class AuthenticationServiceTest {

    @Mock
    private CacheManager cacheManager;
    @Mock
    private BlogUserRepository userRepository;
    @Mock
    private JwtUtility jwtUtility;

    @InjectMocks
    private AuthenticationService authenticationService;


    @Test
    public void refreshToken_shouldReturnNewAccessTokenInDtoObject_whenRefreshTokenExists(){

        String refreshToken = "RefreshToken";
        String newAccessToken = "AccessToken";
        Long userId = 1L;
        BlogUser user = new BlogUser();
        TokenSuccessResponseDto expectedResult = TokenSuccessResponseDto.builder().accessToken(newAccessToken).build();

        Mockito.when(cacheManager.fetch(refreshToken,Long.class)).thenReturn(Optional.of(userId));
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(jwtUtility.generateAccessToken(user)).thenReturn(newAccessToken);

        TokenSuccessResponseDto actualResult = authenticationService.refreshToken(refreshToken);

        assertThat(actualResult.getAccessToken()).isEqualTo(expectedResult.getAccessToken());
    }

    @Test
    public void refreshToken_shouldThrowTokenVerificationException_whenRefreshTokenDoesNotExist(){
        String refreshToken = "RefreshToken";

        Mockito.when(cacheManager.fetch(refreshToken, Long.class)).thenReturn(Optional.empty());

        assertThatThrownBy(()->authenticationService.refreshToken(refreshToken))
                .isInstanceOf(TokenVerificationException.class)
                .hasMessage("401 UNAUTHORIZED \"Authentication failure: Token missing, invalid, revoked or expired\"");
    }

    @Test
    public void refreshToken_shouldThrowUserNotFountException_whenUserWithIdFromRefreshTokenDoesNotExist(){
        String refreshToken = "RefreshToken";
        String newAccessToken = "AccessToken";
        Long userId = 1L;

        Mockito.when(cacheManager.fetch(refreshToken,Long.class)).thenReturn(Optional.of(userId));
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->authenticationService.refreshToken(refreshToken))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("400 BAD_REQUEST \"User not found\"");


    }
}
