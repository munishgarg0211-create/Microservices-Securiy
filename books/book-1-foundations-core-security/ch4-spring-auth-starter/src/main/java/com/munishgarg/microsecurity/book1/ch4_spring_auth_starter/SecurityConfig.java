package com.munishgarg.microsecurity.book1.ch4_spring_auth_starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
// This configuration only kicks in if corp.security.global.enabled is true or missing
@ConditionalOnProperty(prefix = "corp.security.global", name = "enabled", matchIfMissing = true)
public class SecurityConfig {

    private final GlobalSecurityProperties properties;

    public SecurityConfig(GlobalSecurityProperties properties) {
        this.properties = properties;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Enforce authentication at the network edge filter level
                        .anyRequest().authenticated()
                )
                // Configure OAuth2 JWT validation using the globally injected Issuer URI
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())))
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Enforce the Platform Security team's central Issuer URI requirement
        return NimbusJwtDecoder.withIssuerLocation(properties.getIssuerUri()).build();
    }
}
