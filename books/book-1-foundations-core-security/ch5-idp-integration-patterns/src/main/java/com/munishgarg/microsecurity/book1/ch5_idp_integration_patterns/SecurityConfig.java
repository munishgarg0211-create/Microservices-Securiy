package com.munishgarg.microsecurity.book1.ch5_idp_integration_patterns;

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
        return http
                .authorizeHttpRequests(auth -> auth
                        // We require authentication for all requests in this demo.
                        .anyRequest().authenticated()
                )
                // The crucial line: We delegate authentication entirely to the Identity Provider (IdP) via OAuth2/OIDC.
                // We do NOT configure a UserDetailsService or ask for passwords in our application.
                .oauth2Login(Customizer.withDefaults())
                .build();
    }
}
