package com.karatesan.WebAppApi.utility;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@RequiredArgsConstructor
public class TokenHeaderProvider {

    private final HttpServletRequest httpServletRequest;
    public static final String REFRESH_TOKEN_HEADER = "X-Refresh-Token";
    public static final String ACCESS_TOKEN_HEADER = "Authorization";


    public Optional<String> getRefreshToken(){
        return getToken(REFRESH_TOKEN_HEADER);
    }

    public Optional<String> getAccessToken(){
        return getToken(ACCESS_TOKEN_HEADER);
    }

    public Optional<String>  getToken(String name){
        return Optional.ofNullable(httpServletRequest.getHeader(name))
                .filter(value -> !value.isEmpty());
    }


}
