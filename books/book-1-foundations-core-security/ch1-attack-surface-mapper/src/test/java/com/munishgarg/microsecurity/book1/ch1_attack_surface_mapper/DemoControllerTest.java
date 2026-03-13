package com.munishgarg.microsecurity.book1.ch1_attack_surface_mapper;

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
    void shouldReturnOptimizedSurfaceWhenExposureIsLow() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("endpoints", "10")
                .param("exposed", "1")
                .param("mfaEnforced", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("optimized"))
                .andExpect(jsonPath("$.riskScore").value(18));
    }

    @Test
    void shouldReturnOverExposedWhenExposureIsHigh() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("endpoints", "10")
                .param("exposed", "5")
                .param("mfaEnforced", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("over-exposed"))
                .andExpect(jsonPath("$.riskScore").value(88));
    }

    @Test
    void shouldIncludeStandardMetadata() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch1-attack-surface-mapper"))
                .andExpect(jsonPath("$.concept").value("Attack Surface Analysis"))
                .andExpect(jsonPath("$.controlFamily").value("THREAT"));
    }
}
