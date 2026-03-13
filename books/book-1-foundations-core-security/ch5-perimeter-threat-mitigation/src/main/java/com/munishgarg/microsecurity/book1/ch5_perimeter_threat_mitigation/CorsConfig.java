package com.munishgarg.microsecurity.book1.ch5_perimeter_threat_mitigation;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    /**
     * SECURE CORS POLICY:
     * - Never use allowedOrigins("*") in combination with allowCredentials(true).
     * - Explicitly list the exact Origins that are permitted to interact with this API.
     * - Explicitly list the HTTP Methods allowed.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Anti-pattern: configuration.setAllowedOrigins(List.of("*"));
        // Secure: Explicitly define trusted frontends
        configuration.setAllowedOrigins(List.of("https://trusted-frontend.example.com"));
        
        // Explicitly define allowed methods
        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS"));
        
        // Define allowed headers
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        
        // If passing cookies or authorization headers, this MUST be true, 
        // which makes allowOrigins("*") illegal in modern browsers.
        configuration.setAllowCredentials(true);
        
        // Cache the preflight response for 1 hour to reduce OPTIONS request overhead
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
