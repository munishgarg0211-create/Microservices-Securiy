package com.munishgarg.microsecurity.book1.ch4_auth_anti_patterns_lab;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Crucial for @PreAuthorize
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Disabled for simple API testing
                .authorizeHttpRequests(auth -> auth
                        // Require authentication globally, but we leave authorization to the methods
                        // This allows us to demonstrate Missing Function Level Access Control
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails alice = User.withDefaultPasswordEncoder()
                .username("alice")
                .password("password")
                .roles("USER")
                .build();

        UserDetails bob = User.withDefaultPasswordEncoder()
                .username("bob")
                .password("password")
                .roles("USER")
                .build();
                
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(alice, bob, admin);
    }
}
