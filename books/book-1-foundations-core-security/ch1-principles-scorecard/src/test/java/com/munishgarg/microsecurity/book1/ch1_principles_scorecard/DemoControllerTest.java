package com.munishgarg.microsecurity.book1.ch1_principles_scorecard;

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
    void shouldReturnCompliantWhenAllPrinciplesMet() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("defenseInDepth", "true")
                .param("leastPrivilege", "true")
                .param("failSecurely", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("compliant"))
                .andExpect(jsonPath("$.complianceScore").value("100%"))
                .andExpect(jsonPath("$.riskScore").value(15));
    }

    @Test
    void shouldReturnNonCompliantWhenAnyPrincipleFails() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("defenseInDepth", "true")
                .param("leastPrivilege", "false")
                .param("failSecurely", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("non-compliant"))
                .andExpect(jsonPath("$.riskScore").value(85));
    }

    @Test
    void shouldIncludeStandardMetadata() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch1-principles-scorecard"))
                .andExpect(jsonPath("$.concept").value("Security Principles Scorecard"))
                .andExpect(jsonPath("$.controlFamily").value("GOVERNANCE"));
    }
}
