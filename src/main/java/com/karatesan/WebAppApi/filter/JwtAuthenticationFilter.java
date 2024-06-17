package com.karatesan.WebAppApi.filter;

import com.karatesan.WebAppApi.exception.TokenVerificationException;
import com.karatesan.WebAppApi.services.TokenRevocationService;
import com.karatesan.WebAppApi.utility.ApiEndpointSecurityInspector;
import com.karatesan.WebAppApi.utility.JwtUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private  final JwtUtility jwtUtility;
    private final TokenRevocationService tokenRevocationService;
    private final ApiEndpointSecurityInspector apiEndpointSecurityInspector;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    {
        System.out.println("W filterze");
        boolean unsecureRequestBeingInvoked = apiEndpointSecurityInspector.isUnsecureRequest(request);

        if(!unsecureRequestBeingInvoked){
            final String authorization = request.getHeader(AUTHORIZATION_HEADER);

            if(authorization !=null){
                if(authorization.startsWith(BEARER_PREFIX)){
                    final String token = authorization.replace(BEARER_PREFIX, "");
                    final boolean isRevoked = tokenRevocationService.isRevoked(token);
                    if(isRevoked){
                        throw new TokenVerificationException();
                    }
                    final long userId = jwtUtility.extractUserId(token);
                    final List<GrantedAuthority> authority = jwtUtility.getAuthority(token);
                    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, authority);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
