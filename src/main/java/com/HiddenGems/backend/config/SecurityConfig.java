package com.HiddenGems.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/admin/**").access((authentication, context) -> {
                    String role = context.getRequest().getHeader("X-User-Role");
                    return new AuthorizationDecision("admin".equalsIgnoreCase(role));
                })
                .anyRequest().permitAll()
            );

        return http.build();
    }
}