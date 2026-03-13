package com.munishgarg.microsecurity.book1.ch5_gateway_role_demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // Allow local loopback internal requests (simulating unauthenticated backend network)
                        .pathMatchers("/internal/backend/**").permitAll()
                        // The Gateway acts as the perimeter. All traffic must be authenticated here.
                        // We do not rely on the downstream microservices to enforce authentication.
                        .anyExchange().authenticated()
                )
                // For demonstration, we use HTTP Basic. In production, this would be an OAuth2 Resource Server.
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails alice = User.withDefaultPasswordEncoder()
                .username("alice")
                .password("password")
                .roles("USER")
                .build();

        return new MapReactiveUserDetailsService(alice);
    }
}
