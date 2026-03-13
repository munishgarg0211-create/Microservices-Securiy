package com.munishgarg.microsecurity.book1.ch6_circuit_breaker_security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldSucceedWhenDownstreamIsHealthy() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("allow"))
                .andExpect(jsonPath("$.data").value("Success from Downstream"));
    }

    @Test
    void shouldInsecureModeFailWhenDownstreamFails() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("mode", "insecure")
                .param("shouldFail", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("error"))
                .andExpect(jsonPath("$.riskScore").value(95));
    }

    @Test
    void shouldSecureModeTriggerFallbackWhenDownstreamFails() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("mode", "secure")
                .param("shouldFail", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("fallback"))
                .andExpect(jsonPath("$.data").value("Cached/Static Data"))
                .andExpect(jsonPath("$.riskScore").value(10));
    }
}
