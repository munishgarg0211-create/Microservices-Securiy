package com.munishgarg.microsecurity.book1.ch6_defensive_api_contracts;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {
        DeadlineContext.clear();
    }

    @Test
    void shouldAllowWhenWithinDeadline() throws Exception {
        mockMvc.perform(get("/api/demo")
                .header("X-Deadline-Ms", "1000")
                .param("duration", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("allow"))
                .andExpect(jsonPath("$.riskScore").value(15));
    }

    @Test
    void shouldBlockWhenDeadlineExceededBeforeStart() throws Exception {
        mockMvc.perform(get("/api/demo")
                .header("X-Deadline-Ms", "-10")
                .param("duration", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("block"))
                .andExpect(jsonPath("$.expectedBehavior").value("Deadline exceeded! Failing fast to save resources before starting work."));
    }

    @Test
    void shouldBlockWhenDeadlineExceededDuringWork() throws Exception {
        mockMvc.perform(get("/api/demo")
                .header("X-Deadline-Ms", "50")
                .param("duration", "150"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("block"))
                .andExpect(jsonPath("$.expectedBehavior").value("Deadline exceeded during processing! Abandoning results to prevent further cascading delay."));
    }

    @Test
    void shouldIncludeStandardMetadata() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch6-defensive-api-contracts"))
                .andExpect(jsonPath("$.concept").value("Defensive Programming"))
                .andExpect(jsonPath("$.controlFamily").value("RESILIENCE"));
    }
}
