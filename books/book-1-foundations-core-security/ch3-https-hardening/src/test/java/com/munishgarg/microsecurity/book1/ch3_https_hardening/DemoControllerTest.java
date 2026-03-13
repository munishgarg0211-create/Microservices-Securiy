package com.munishgarg.microsecurity.book1.ch3_https_hardening;

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
    void shouldAllowTls12() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("tlsVersion", "1.2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("allow"))
                .andExpect(jsonPath("$.riskScore").value(12));
    }

    @Test
    void shouldDenyDeprecatedTls10() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("tlsVersion", "1.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("deny"))
                .andExpect(jsonPath("$.riskScore").value(90));
    }

    @Test
    void shouldReturnStandardMetadata() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch3-https-hardening"))
                .andExpect(jsonPath("$.concept").value("Transport Layer Security - HTTPS"))
                .andExpect(jsonPath("$.controlFamily").value("TRANSPORT"));
    }
}
