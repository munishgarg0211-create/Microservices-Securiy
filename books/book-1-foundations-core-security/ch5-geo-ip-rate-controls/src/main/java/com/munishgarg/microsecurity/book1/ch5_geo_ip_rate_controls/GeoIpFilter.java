package com.munishgarg.microsecurity.book1.ch5_geo_ip_rate_controls;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class GeoIpFilter extends OncePerRequestFilter {

    // For this demo, imagine this simulates connecting to a local MaxMind GeoIP database
    // We will block requests originating from the fictitious country codes "XX" and "YY"
    private static final Set<String> BLOCKED_COUNTRIES = Set.of("XX", "YY");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // In reality, you'd extract the true client IP from the network packet or X-Forwarded-For
        // and do a fast local DB lookup. Here we mock it via a header for testability.
        String countryCode = request.getHeader("X-Forwarded-Country");

        if (countryCode != null && BLOCKED_COUNTRIES.contains(countryCode.toUpperCase())) {
            // Drop the request immediately. Do not process authorization or business logic.
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Access denied from your region.\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
