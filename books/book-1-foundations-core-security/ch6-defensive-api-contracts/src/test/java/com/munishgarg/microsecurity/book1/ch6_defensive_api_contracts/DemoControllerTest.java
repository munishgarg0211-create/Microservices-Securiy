package com.munishgarg.microsecurity.book1.ch6_defensive_api_contracts;

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
    void shouldSucceedWithinDeadline() throws Exception {
        mockMvc.perform(get("/api/demo")
                .header("X-Deadline-Ms", "500")
                .param("workDurationMs", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("allow"))
                .andExpect(jsonPath("$.data").value("Success Data"));
    }

    @Test
    void shouldFailFastWhenDeadlineAlreadyExceeded() throws Exception {
        mockMvc.perform(get("/api/demo")
                .header("X-Deadline-Ms", "-10") // Already expired
                .param("workDurationMs", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("block"))
                .andExpect(jsonPath("$.expectedBehavior").value(org.hamcrest.Matchers.containsString("Failing fast")));
    }

    @Test
    void shouldAbandonWhenDeadlineExpiresDuringWork() throws Exception {
        mockMvc.perform(get("/api/demo")
                .header("X-Deadline-Ms", "50")
                .param("workDurationMs", "150"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("block"))
                .andExpect(jsonPath("$.expectedBehavior").value(org.hamcrest.Matchers.containsString("Abandoning results")));
    }

    @Test
    void shouldInsecureModeIgnoreDeadline() throws Exception {
        mockMvc.perform(get("/api/demo")
                .header("X-Deadline-Ms", "-10")
                .param("mode", "insecure")
                .param("workDurationMs", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("allow"));
    }
}
