package com.munishgarg.microsecurity.book1.ch3_kafka_security_baseline;

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
    void shouldAllowWhenAllSecurityChecksPass() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("sslEnabled", "true")
                .param("saslAuthenticated", "true")
                .param("aclAuthorized", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("allow"))
                .andExpect(jsonPath("$.riskScore").value(14));
    }

    @Test
    void shouldDenyWhenSslIsMissing() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("sslEnabled", "false")
                .param("saslAuthenticated", "true")
                .param("aclAuthorized", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("deny"))
                .andExpect(jsonPath("$.riskScore").value(96));
    }

    @Test
    void shouldDenyWhenAclMissing() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("sslEnabled", "true")
                .param("saslAuthenticated", "true")
                .param("aclAuthorized", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("deny"));
    }

    @Test
    void shouldReturnStandardMetadata() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch3-kafka-security-baseline"))
                .andExpect(jsonPath("$.concept").value("Event Bus Security - Kafka"))
                .andExpect(jsonPath("$.controlFamily").value("MESSAGING"));
    }
}
