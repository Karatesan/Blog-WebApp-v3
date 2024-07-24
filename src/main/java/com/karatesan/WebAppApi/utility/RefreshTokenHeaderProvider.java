package com.karatesan.WebAppApi.utility;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
//Currently not used, moved to annotation based header
@Component
@RequiredArgsConstructor
public class RefreshTokenHeaderProvider {

    private final HttpServletRequest httpServletRequest;
    public static final String REFRESH_TOKEN_HEADER = "X-Refresh-Token";


    public Optional<String> getRefreshToken(){
        System.out.println(httpServletRequest.toString());
        return Optional.ofNullable(httpServletRequest.getHeader(REFRESH_TOKEN_HEADER))
                .filter(value -> !value.isEmpty());
    }

}
