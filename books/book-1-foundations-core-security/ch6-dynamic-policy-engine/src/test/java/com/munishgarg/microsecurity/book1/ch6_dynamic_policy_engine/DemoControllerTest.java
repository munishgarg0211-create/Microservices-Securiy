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
    void shouldAllowWhenAllPoliciesPass() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("imageSigned", "true")
                .param("hasSbom", "true")
                .param("criticalVulns", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("allow"))
                .andExpect(jsonPath("$.riskScore").value(0));
    }

    @Test
    void shouldBlockWhenPoliciesFail() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("imageSigned", "false")
                .param("hasSbom", "true")
                .param("criticalVulns", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("block"))
                .andExpect(jsonPath("$.riskScore").value(50))
                .andExpect(jsonPath("$.violations[0]").value("require-signed-image"));
    }

    @Test
    void shouldInsecureModeIgnorePolicyViolations() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("mode", "insecure")
                .param("imageSigned", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("allow"))
                .andExpect(jsonPath("$.riskScore").value(95));
    }
}
