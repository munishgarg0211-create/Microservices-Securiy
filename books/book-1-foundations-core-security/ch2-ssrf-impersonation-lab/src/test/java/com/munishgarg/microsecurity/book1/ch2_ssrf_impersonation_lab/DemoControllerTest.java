package com.munishgarg.microsecurity.book1.ch2_ssrf_impersonation_lab;

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
    void shouldAllowPublicRequests() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("url", "https://public-api.example.com/data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("allow"))
                .andExpect(jsonPath("$.isInternalResource").value(false));
    }

    @Test
    void shouldDenyInternalMetadataRequests() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("url", "http://169.254.169.254/latest/meta-data/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("deny"))
                .andExpect(jsonPath("$.isInternalResource").value(true))
                .andExpect(jsonPath("$.riskScore").value(98));
    }

    @Test
    void shouldDenyLocalhostRequests() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("url", "http://localhost:9090/admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("deny"))
                .andExpect(jsonPath("$.isInternalResource").value(true));
    }

    @Test
    void shouldReturnStandardMetadata() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch2-ssrf-impersonation-lab"))
                .andExpect(jsonPath("$.concept").value("OWASP Top 10 - SSRF"))
                .andExpect(jsonPath("$.controlFamily").value("THREAT"));
    }
}
