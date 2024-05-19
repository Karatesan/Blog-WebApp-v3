package com.karatesan.WebAppApi.config;

import com.karatesan.WebAppApi.model.security.filter.AuthoritiesLoggingAfterFilter;
import com.karatesan.WebAppApi.model.security.filter.JWTTokenGeneratorFilter;
import com.karatesan.WebAppApi.model.security.filter.JWTTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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


    //to tworze po to, zeby moc wykorzystac w controllerze podzas loginu
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
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
        configuration.setAllowedOrigins(List.of("*"));
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
                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors((cors)->cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(new JWTTokenGeneratorFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(),UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new JWTTokenValidatorFilter(), UsernamePasswordAuthenticationFilter.class)
                //.addFilterBefore(new jwtTokenFilter2(),UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> requests
                        //.requestMatchers("/blogpost").hasAuthority("READ_PRIVILEGE")
                        .requestMatchers("/login").permitAll())
                        //.anyRequest().authenticated())
        ;
        return http.build();
    }
}
