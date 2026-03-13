package com.munishgarg.microsecurity.book1.ch5_perimeter_threat_mitigation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityHeadersTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void response_ShouldContainStrictSecurityHeaders() throws Exception {
        mockMvc.perform(get("/api/demo").secure(true))
                .andExpect(status().isOk())
                // Assert HSTS
                .andExpect(header().string("Strict-Transport-Security", "max-age=31536000 ; includeSubDomains"))
                // Assert Frame Options (Clickjacking protection)
                .andExpect(header().string("X-Frame-Options", "DENY"))
                // Assert Content Type Options (MIME sniffing protection)
                .andExpect(header().string("X-Content-Type-Options", "nosniff"))
                // Assert Content Security Policy
                .andExpect(header().string("Content-Security-Policy", "default-src 'self'; script-src 'self'; style-src 'self'; object-src 'none'"));
    }

    @Test
    void corsPreflight_FromAllowedOrigin_ShouldSucceed() throws Exception {
        // Assert that a known trusted origin receives the Access-Control-Allow-Origin header
        mockMvc.perform(options("/api/demo")
                        .header("Origin", "https://trusted-frontend.example.com")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "https://trusted-frontend.example.com"))
                .andExpect(header().string("Access-Control-Allow-Credentials", "true"));
    }

    @Test
    void corsPreflight_FromUntrustedOrigin_ShouldDeny() throws Exception {
        // Assert that an untrusted origin does NOT receive the Access-Control-Allow-Origin header
        // Spring Security typically returns 403 Forbidden for bad CORS requests,
        // but even if it returns 200, the absence of the 'Access-Control-Allow-Origin' header 
        // causes the browser to block the actual request.
        mockMvc.perform(options("/api/demo")
                        .header("Origin", "https://malicious-attacker.com")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isForbidden())
                .andExpect(header().doesNotExist("Access-Control-Allow-Origin"));
    }
}
