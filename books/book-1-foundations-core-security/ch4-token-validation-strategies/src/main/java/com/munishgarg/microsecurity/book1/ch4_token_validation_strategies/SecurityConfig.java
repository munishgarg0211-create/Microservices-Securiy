package com.munishgarg.microsecurity.book1.ch4_token_validation_strategies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Basic authorization logic; deeper logic is usually in @PreAuthorize
                        .anyRequest().authenticated()
                )
                // Wires up the JWT decoder explicitly so we can attach custom validators
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())))
                .build();
    }

    /**
     * Creates a custom JwtDecoder that relies on a specific JWK Set URI,
     * but injects additional validation requirements (Issuer and Audience).
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        // In a real environment, this URI points to your IdP's well-known JWKS endpoint.
        // For this self-contained demo, we configure it but mock the decoder in tests.
        String jwkSetUri = "https://example.com/oauth2/default/v1/keys";
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();

        // Standard validator (checks expiry and signature)
        OAuth2TokenValidator<Jwt> defaultValidator = JwtValidators.createDefaultWithIssuer("https://example.com/oauth2/default");
        
        // Custom Audience Validator (e.g., ensuring the token was minted specifically for this microservice)
        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator("api://demo-service");

        // Combine them using a Delegating Validator
        OAuth2TokenValidator<Jwt> customValidator = new DelegatingOAuth2TokenValidator<>(defaultValidator, audienceValidator);

        jwtDecoder.setJwtValidator(customValidator);

        return jwtDecoder;
    }
}
