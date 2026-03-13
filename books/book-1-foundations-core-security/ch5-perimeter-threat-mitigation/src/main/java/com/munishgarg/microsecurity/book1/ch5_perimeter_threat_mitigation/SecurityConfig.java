package com.munishgarg.microsecurity.book1.ch5_perimeter_threat_mitigation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 1. Enable CORS using the ConfigurationSource bean we created
                .cors(Customizer.withDefaults())
                
                // 2. Configure Strict HTTP Security Headers
                .headers(headers -> headers
                    // Prevent Clickjacking by denying rendering in <frame> or <iframe>
                    // Alternatives: SAMEORIGIN (if you need to frame your own app)
                    .frameOptions(frame -> frame.deny())
                    
                    // Enforce HTTPS routing (HSTS)
                    .httpStrictTransportSecurity(hsts -> hsts
                        .includeSubDomains(true)
                        .maxAgeInSeconds(31536000) // 1 Year
                    )
                    
                    // Prevent MIME-sniffing attacks (forces browser to respect Content-Type)
                    .contentTypeOptions(Customizer.withDefaults())
                    
                    // X-XSS-Protection (Legacy, but good defense in depth for older browsers)
                    .xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
                    
                    // Content Security Policy (CSP):
                    // Extremely restrictive: Only load scripts/styles from the same origin.
                    // Absolutely no 'unsafe-inline' or 'unsafe-eval'.
                    .contentSecurityPolicy(csp -> csp
                        .policyDirectives("default-src 'self'; script-src 'self'; style-src 'self'; object-src 'none'")
                    )
                )
                
                // For demonstration, we allow anonymous access so we can focus solely on headers and CORS testing in MockMvc
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }
}
