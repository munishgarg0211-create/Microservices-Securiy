package com.munishgarg.microsecurity.book1.ch6_dynamic_policy_engine;

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
    void shouldAllowWhenPolicyPassed() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("imageSigned", "true")
                .param("hasSbom", "true")
                .param("criticalVulns", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("allow"))
                .andExpect(jsonPath("$.riskScore").value(10));
    }

    @Test
    void shouldBlockWhenPolicyViolationDetected() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("imageSigned", "false")
                .param("hasSbom", "true")
                .param("criticalVulns", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("block"))
                .andExpect(jsonPath("$.riskScore").value(60));
    }

    @Test
    void shouldIncludeStandardMetadata() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch6-dynamic-policy-engine"))
                .andExpect(jsonPath("$.concept").value("Externalized Policy Management"))
                .andExpect(jsonPath("$.controlFamily").value("POLICY"));
    }
}
