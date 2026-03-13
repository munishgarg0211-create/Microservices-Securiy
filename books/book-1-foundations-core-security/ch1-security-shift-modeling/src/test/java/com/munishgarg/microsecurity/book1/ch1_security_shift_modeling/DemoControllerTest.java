package com.munishgarg.microsecurity.book1.ch1_security_shift_modeling;

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
    void shouldReturnShieldedWhenShiftLeftIsEffective() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("designReview", "true")
                .param("threatModeling", "true")
                .param("earlyMitigationRate", "90"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("shielded"))
                .andExpect(jsonPath("$.riskScore").value(12));
    }

    @Test
    void shouldReturnExposedWhenModelingIsSkipped() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("designReview", "true")
                .param("threatModeling", "false")
                .param("earlyMitigationRate", "90"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("exposed"))
                .andExpect(jsonPath("$.riskScore").value(88));
    }

    @Test
    void shouldReturnExposedWhenMitigationRateIsLow() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("designReview", "true")
                .param("threatModeling", "true")
                .param("earlyMitigationRate", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("exposed"));
    }

    @Test
    void shouldIncludeStandardMetadata() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch1-security-shift-modeling"))
                .andExpect(jsonPath("$.concept").value("Security Shift-Left Modeling"))
                .andExpect(jsonPath("$.controlFamily").value("THREAT"));
    }
}
