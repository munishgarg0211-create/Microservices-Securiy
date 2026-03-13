package com.munishgarg.microsecurity.book1.ch5_edge_observability;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ObservabilitySecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private org.springframework.context.ApplicationContext context;

    @org.springframework.boot.test.context.TestConfiguration
    public static class TestConfig {
        @org.springframework.web.bind.annotation.RestController
        public static class MockPrometheusController {
            @org.springframework.web.bind.annotation.GetMapping("/actuator/prometheus")
            public String prometheus() {
                return "jvm_memory_used_bytes 12345678";
            }
        }
    }

    @Test
    void publicApi_ShouldGenerateAndReturnTraceId() throws Exception {
        // We assert that hitting the public API succeeds...
        mockMvc.perform(get("/api/process"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(header().exists("X-Trace-Id"));
    }

    @Test
    void prometheusEndpoint_WithoutCredentials_ShouldReturn401() throws Exception {
        // Without authentication, access is denied.
        mockMvc.perform(get("/actuator/prometheus"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void prometheusEndpoint_WithValidScraperCredentials_ShouldReturn200() throws Exception {
        // With the correct basic auth configured in SecurityConfig, access is allowed.
        mockMvc.perform(get("/actuator/prometheus")
                        .with(httpBasic("prometheus-scraper", "secure-token-12345")))
                .andExpect(status().isOk());
    }
}
