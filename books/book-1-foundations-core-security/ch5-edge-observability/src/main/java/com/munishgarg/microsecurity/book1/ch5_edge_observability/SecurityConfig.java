package com.munishgarg.microsecurity.book1.ch5_edge_observability;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuditFilter auditFilter;

    public SecurityConfig(AuditFilter auditFilter) {
        this.auditFilter = auditFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Disabled for local testing/demo purposes
                
                // Ensure AuditFilter runs before Authentication so even rejected requests are logged with Trace IDs
                .addFilterBefore(auditFilter, UsernamePasswordAuthenticationFilter.class)
                
                .authorizeHttpRequests(auth -> auth
                        // 1. Secure Observability Endpoints
                        // EndpointRequest.toAnyEndpoint() matches all /actuator/** endpoints.
                        // We strictly require the "ACTUATOR_ADMIN" role.
                        .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR_ADMIN")
                        
                        // 2. Allow public access to business API endpoints
                        .requestMatchers("/api/**").permitAll()
                        
                        .anyRequest().authenticated()
                )
                // We use HTTP Basic Auth for Prometheus scrapers to authenticate
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    /**
     * Define an in-memory user specifically for system-to-system scraping securely.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails scraperSystemAccount = User.builder()
                .username("prometheus-scraper")
                .password("{noop}secure-token-12345") // Use a real hash/vault in production
                .roles("ACTUATOR_ADMIN")
                .build();

        return new InMemoryUserDetailsManager(scraperSystemAccount);
    }
}
