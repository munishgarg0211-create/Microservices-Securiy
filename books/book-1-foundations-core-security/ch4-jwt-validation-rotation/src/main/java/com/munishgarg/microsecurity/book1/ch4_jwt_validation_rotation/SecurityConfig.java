package com.munishgarg.microsecurity.book1.ch4_jwt_validation_rotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // The "/api/demo/insecure" endpoint intentionally permits all access
                // to demonstrate what happens when authorization controls are bypassed.
                .requestMatchers("/api/demo/insecure").permitAll()
                
                // All other endpoints (including "/api/demo/secure") require authentication.
                // Method-level security (@PreAuthorize) will enforce object ownership.
                .anyRequest().authenticated()
            )
            // Configure the app as an OAuth2 Resource Server that validates JWTs.
            // Spring Boot automatically configures a JwtDecoder using the jwk-set-uri in application.yml.
            // The decoder parses the JWT, validates the signature using the cached public key,
            // and automatically rotates keys based on the Identity Provider's cache-control headers.
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
            
        return http.build();
    }
}
