package com.karatesan.WebAppApi.config;

import com.karatesan.WebAppApi.model.security.filter.JWTTokenGeneratorFilter;
import com.karatesan.WebAppApi.model.security.filter.JWTTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_STAFF > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

//    @Bean
//    public DefaultWebSecurityExpressionHandler customWebSecurityExpressionHandler(){
//        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
//        expressionHandler.setRoleHierarchy(roleHierarchy());
//        return expressionHandler;
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        //By default, browsers restrict certain headers in cross-origin requests for security reasons.
        // The Authorization header is one of those headers that is not automatically included in cross-origin requests.
        //Without this configuration, when a request is made from a different origin (e.g., from http://localhost:4200 to your server),
        // the browser may block the client-side JavaScript code from accessing the Authorization header in the response
        // due to the same-origin policy.
        configuration.setExposedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception{
//hasRole - musi miec role czyli ROLE_COS
// hasAuthority - to jest do privilegow czyli WRITE_PRIVILEGE
        http
                .cors((cors)->cors.configurationSource(corsConfigurationSource()))
                .csrf().disable()
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/blogpost").hasAuthority("READ_PRIVILEGE")
                );
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }
}
