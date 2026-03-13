package com.munishgarg.microsecurity.book1.ch3_protocol_threat_comparison;

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
    void shouldApproveSecuregRPC() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("protocol", "gRPC + TLS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("approved-transport"))
                .andExpect(jsonPath("$.riskScore").value(12));
    }

    @Test
    void shouldRejectPlainHttp() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("protocol", "Plain HTTP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("rejected-transport"))
                .andExpect(jsonPath("$.riskScore").value(95));
    }

    @Test
    void shouldReturnStandardMetadata() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch3-protocol-threat-comparison"))
                .andExpect(jsonPath("$.concept").value("Protocol Security Comparison"))
                .andExpect(jsonPath("$.controlFamily").value("THREAT"));
    }
}
