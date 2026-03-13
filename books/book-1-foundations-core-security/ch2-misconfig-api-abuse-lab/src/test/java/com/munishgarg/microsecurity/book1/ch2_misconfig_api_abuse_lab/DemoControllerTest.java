package com.munishgarg.microsecurity.book1.ch2_misconfig_api_abuse_lab;

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
    void shouldAllowUnderLimit() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("attempts", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("allow"))
                .andExpect(jsonPath("$.riskScore").value(10));
    }

    @Test
    void shouldThrottleOverLimit() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("attempts", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("throttle"))
                .andExpect(jsonPath("$.riskScore").value(92));
    }

    @Test
    void shouldReturnStandardMetadata() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch2-misconfig-api-abuse-lab"))
                .andExpect(jsonPath("$.concept").value("OWASP Top 10 - Security Misconfiguration"))
                .andExpect(jsonPath("$.controlFamily").value("THREAT"));
    }
}
