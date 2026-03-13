package com.munishgarg.microsecurity.book1.ch5_geo_ip_rate_controls;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final GeoIpFilter geoIpFilter;

    public SecurityConfig(GeoIpFilter geoIpFilter) {
        this.geoIpFilter = geoIpFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // We disable CSRF since this is an API demo meant for testing
                .csrf(csrf -> csrf.disable())
                
                // Allow anonymous access so we can cleanly test the rate limiter and geo filter
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                
                // CRITICAL: We inject our custom GeoIpFilter very early in the chain, 
                // BEFORE standard authentication. 
                // If the IP is bad, we drop the connection before wasting CPU cycles 
                // on password hashing or JWT validation.
                .addFilterBefore(geoIpFilter, UsernamePasswordAuthenticationFilter.class)
                
                .build();
    }
}
