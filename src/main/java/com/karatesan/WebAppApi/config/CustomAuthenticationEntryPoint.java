package com.karatesan.WebAppApi.config;

import com.karatesan.WebAppApi.exception.TokenVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;


//entry point obsluguje wyjatki podczas uwierzytelniania w filterChain, czyli jak nie mamy odpwoiednich role/privilege zeby moc gorzystac z danego endpointa np.
@Configuration
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    @SneakyThrows
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {

        handlerExceptionResolver.resolveException(request, response, null, new TokenVerificationException());
    }

}