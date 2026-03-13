package com.munishgarg.microsecurity.book1.ch3_mtls_baseline;

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
    void shouldAllowWhenCertPresentAndTrusted() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("clientCertPresent", "true")
                .param("certTrusted", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("allow"))
                .andExpect(jsonPath("$.riskScore").value(10));
    }

    @Test
    void shouldDenyWhenCertUntrusted() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("clientCertPresent", "true")
                .param("certTrusted", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("deny"))
                .andExpect(jsonPath("$.riskScore").value(92));
    }

    @Test
    void shouldDenyWhenCertMissing() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("clientCertPresent", "false")
                .param("certTrusted", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("deny"));
    }

    @Test
    void shouldReturnStandardMetadata() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch3-mtls-baseline"))
                .andExpect(jsonPath("$.concept").value("Transport Layer Security - mTLS"))
                .andExpect(jsonPath("$.controlFamily").value("TRANSPORT"));
    }
}
