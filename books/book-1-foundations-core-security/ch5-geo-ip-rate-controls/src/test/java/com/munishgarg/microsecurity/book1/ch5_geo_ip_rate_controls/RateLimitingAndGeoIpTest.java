package com.munishgarg.microsecurity.book1.ch5_geo_ip_rate_controls;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RateLimitingAndGeoIpTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void geoIpFilter_WithBlockedCountry_ShouldReturn403Forbidden() throws Exception {
        // Simulating traffic from "XX" which we blocked in GeoIpFilter
        mockMvc.perform(get("/api/demo")
                .header("X-Forwarded-Country", "XX"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value("Access denied from your region."));
    }

    @Test
    void geoIpFilter_WithAllowedCountry_ShouldReturn200Ok() throws Exception {
        // "US" is not in the blocklist
        mockMvc.perform(get("/api/demo")
                .header("X-Forwarded-Country", "US"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    void rateLimiter_WhenExceeding5Requests_ShouldReturn429TooManyRequests() throws Exception {
        // Our application.yml configures 5 requests per 10 seconds.
        // We will fire exactly 5 successful requests.
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(get("/api/demo")
                    .header("X-Forwarded-Country", "US"))
                    .andExpect(status().isOk());
        }

        // The 6th request should trip the Resilience4j circuit and hit our
        // GlobalExceptionHandler
        mockMvc.perform(get("/api/demo")
                .header("X-Forwarded-Country", "US"))
                .andExpect(status().isTooManyRequests())
                .andExpect(jsonPath("$.error").value("Rate limit exceeded. Please try again later."));
    }
}
