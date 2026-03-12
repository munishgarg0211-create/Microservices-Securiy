package com.munishgarg.microsecurity.book1.ch4_oauth2_grants_demo;

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
    void authCodeEndpoint_ShouldRedirectToLogin_WhenUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/demo/auth-code"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void clientCredentials_ShouldRedirectToLogin_WhenUnauthenticated() throws Exception {
        // Even though this is machine-to-machine, the endpoint itself is protected by default
        // because we only permitted /api/demo/public/** in SecurityConfig
        mockMvc.perform(get("/api/demo/client-credentials"))
                .andExpect(status().is3xxRedirection());
    }
}
