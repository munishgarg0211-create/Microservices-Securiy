package com.munishgarg.microsecurity.book1.ch2_owasp_bola_lab;

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
    void shouldAllowOwnerAccess() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("actor", "bob")
                .param("resourceId", "bob_profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("allow"))
                .andExpect(jsonPath("$.riskScore").value(10));
    }

    @Test
    void shouldDenyUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("actor", "alice")
                .param("resourceId", "bob_profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("deny"))
                .andExpect(jsonPath("$.riskScore").value(95));
    }

    @Test
    void shouldReturnStandardMetadata() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch2-owasp-bola-lab"))
                .andExpect(jsonPath("$.concept").value("OWASP Top 10 - BOLA"))
                .andExpect(jsonPath("$.controlFamily").value("AUTHZ"));
    }
}
