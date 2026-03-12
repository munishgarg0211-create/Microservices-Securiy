package com.munishgarg.microsecurity.book1.ch4_oauth2_grants_demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Public endpoint to test unauthenticated access or client-credentials
                .requestMatchers("/api/demo/public/**").permitAll()
                // All other endpoints require the user to be authenticated
                .anyRequest().authenticated()
            )
            // 1. Authorization Code Grant setup:
            // This enables the application to act as an OAuth2 Client that authenticates
            // users via a browser redirect to the Identity Provider (e.g. Keycloak).
            .oauth2Login(Customizer.withDefaults())
            
            // 2. Client Credentials Grant & Token Management:
            // This enables internal OAuth2 client configuration, allowing the app
            // to fetch machine-to-machine tokens for backend calls without user involvement.
            .oauth2Client(Customizer.withDefaults());

        return http.build();
    }
}
