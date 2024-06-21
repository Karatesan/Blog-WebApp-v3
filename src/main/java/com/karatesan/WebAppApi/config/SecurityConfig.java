package com.karatesan.WebAppApi.config;
import com.karatesan.WebAppApi.filter.CsrfCookieFilter;
import com.karatesan.WebAppApi.filter.JwtAuthenticationFilter;
import com.karatesan.WebAppApi.utility.ApiEndpointSecurityInspector;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor

public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final ApiEndpointSecurityInspector apiEndpointSecurityInspector;


//    @Bean
//    public AuthenticationManager noopAuthenticationManager() {
//        return authentication -> {
//            throw new AuthenticationServiceException("Authentication is disabled");
//        };
//    }

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        requestAttributeHandler.setCsrfRequestAttributeName("_csrf");

        return http
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(csrf->csrf.csrfTokenRequestHandler(requestAttributeHandler).ignoringRequestMatchers(apiEndpointSecurityInspector.getPublicGetEndpoints().toArray(String[]::new))
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .exceptionHandling(exceptionConfigurer -> exceptionConfigurer.authenticationEntryPoint(customAuthenticationEntryPoint))
                .sessionManagement(sessionConfigurer -> sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authManager ->{
                    authManager
                            .requestMatchers(HttpMethod.GET,apiEndpointSecurityInspector.getPublicGetEndpoints().toArray(String[]::new)).permitAll()
                            .requestMatchers(HttpMethod.POST,apiEndpointSecurityInspector.getPublicPostEndpoints().toArray(String[]::new)).permitAll()
                            .requestMatchers(HttpMethod.PUT,apiEndpointSecurityInspector.getPublicPutEndpoints().toArray(String[]::new)).permitAll()
                            .requestMatchers("/blogpost").hasRole("USERSss")
                            .anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new CsrfCookieFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        final var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "X-Refresh-Token", "Origin", "Content-Type", "Accept"));

        final var corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return corsConfigurationSource;
    }



}


