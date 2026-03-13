package com.munishgarg.microsecurity.book1.ch1_sdlc_security_pipeline;

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
    void shouldPassPipelineWhenAllScansSuccess() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("sastPassed", "true")
                .param("scaPassed", "true")
                .param("criticalVulns", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("pass"))
                .andExpect(jsonPath("$.riskScore").value(10));
    }

    @Test
    void shouldBlockPipelineWhenCriticalVulnsExist() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("sastPassed", "true")
                .param("scaPassed", "true")
                .param("criticalVulns", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("block"))
                .andExpect(jsonPath("$.riskScore").value(90));
    }

    @Test
    void shouldBlockPipelineWhenSastFails() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("sastPassed", "false")
                .param("scaPassed", "true")
                .param("criticalVulns", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("block"));
    }

    @Test
    void shouldIncludeStandardMetadata() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch1-sdlc-security-pipeline"))
                .andExpect(jsonPath("$.concept").value("Secure SDLC Pipeline"))
                .andExpect(jsonPath("$.controlFamily").value("POLICY"));
    }
}
