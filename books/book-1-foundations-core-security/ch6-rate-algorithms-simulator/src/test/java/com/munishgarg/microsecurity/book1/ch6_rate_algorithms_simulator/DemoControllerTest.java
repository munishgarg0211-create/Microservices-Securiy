package com.munishgarg.microsecurity.book1.ch6_rate_algorithms_simulator;

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
    void shouldAllowWhenAlgorithmHasCapacity() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("algorithm", "token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("allow"))
                .andExpect(jsonPath("$.riskScore").value(15));
    }

    @Test
    void shouldThottleWhenCapacityExceeded() throws Exception {
        // First few should pass
        for(int i=0; i<10; i++) {
            mockMvc.perform(get("/api/demo").param("algorithm", "leaky"));
        }
        
        // This one should likely fail (capacity is 10)
        mockMvc.perform(get("/api/demo")
                .param("algorithm", "leaky"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("throttle"))
                .andExpect(jsonPath("$.riskScore").value(88));
    }

    @Test
    void shouldIncludeStandardMetadata() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch6-rate-algorithms-simulator"))
                .andExpect(jsonPath("$.concept").value("Rate Limiting Algorithms"))
                .andExpect(jsonPath("$.controlFamily").value("RESILIENCE"));
    }
}
