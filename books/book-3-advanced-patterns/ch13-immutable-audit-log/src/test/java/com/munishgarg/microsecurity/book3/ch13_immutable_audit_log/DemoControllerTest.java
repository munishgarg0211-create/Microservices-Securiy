package com.munishgarg.microsecurity.book3.ch13_immutable_audit_log;

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
    void shouldServeSecureDemoPayload() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch13-immutable-audit-log"))
                .andExpect(jsonPath("$.mode").value("secure"))
                .andExpect(jsonPath("$.controlFamily").isNotEmpty())
                .andExpect(jsonPath("$.controlDecision").isNotEmpty());
    }

    @Test
    void shouldServeInsecureDemoPayload() throws Exception {
        mockMvc.perform(get("/api/demo").param("mode", "insecure"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch13-immutable-audit-log"))
                .andExpect(jsonPath("$.mode").value("insecure"))
                .andExpect(jsonPath("$.expectedBehavior").isNotEmpty());
    }
}
