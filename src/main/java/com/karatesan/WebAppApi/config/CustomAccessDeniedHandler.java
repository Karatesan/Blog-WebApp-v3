package com.karatesan.WebAppApi.config;

import com.karatesan.WebAppApi.exception.CustomAccessDeniedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
@Configuration
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    HandlerExceptionResolver handlerExceptionResolver;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        handlerExceptionResolver.resolveException(request,response,null, new CustomAccessDeniedException());
    }

}
