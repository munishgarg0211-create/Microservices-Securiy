package com.munishgarg.microsecurity.book1.ch4_federation_multitenancy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Essential for @PreAuthorize
public class SecurityConfig {

    private final TenantJwtAuthenticationConverter tenantJwtConverter;

    public SecurityConfig(TenantJwtAuthenticationConverter tenantJwtConverter) {
        this.tenantJwtConverter = tenantJwtConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Permit all at filter level to allow fine-grained @PreAuthorize to handle the matrix
                        .anyRequest().permitAll()
                )
                // Configure this service as an OAuth2 Resource Server accepting JWTs
                .oauth2ResourceServer(oauth2 -> oauth2
                        // Supply our custom converter to map the tenant_id claim into a Spring GrantedAuthority
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(tenantJwtConverter))
                )
                .build();
    }
}
