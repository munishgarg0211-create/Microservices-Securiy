package com.munishgarg.microsecurity.book1.ch4_auth_anti_patterns_lab;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
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

    // --- BOLA / IDOR Tests ---

    @Test
    void vulnerableDataEndpoint_AllowsAliceToReadBobsData() throws Exception {
        // Anti-pattern: Alice authenticated, but requesting Bob's data
        mockMvc.perform(get("/api/lab/vulnerable/data/bob")
                        .with(httpBasic("alice", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner").value("bob")); 
                // Alice just successfully stole Bob's data!
    }

    @Test
    void secureDataEndpoint_AllowsAliceToReadAlicesData() throws Exception {
        // Secure pattern: Alice requesting her own data
        mockMvc.perform(get("/api/lab/secure/data/alice")
                        .with(httpBasic("alice", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner").value("alice"));
    }

    @Test
    void secureDataEndpoint_BlocksAliceFromReadingBobsData() throws Exception {
        // Secure pattern: Alice requesting Bob's data is blocked by @PreAuthorize
        mockMvc.perform(get("/api/lab/secure/data/bob")
                        .with(httpBasic("alice", "password")))
                .andExpect(status().isForbidden()); 
                // Returns 403 Forbidden
    }


    // --- Missing Function Level Access Control Tests ---

    @Test
    void vulnerableAdminEndpoint_AllowsAliceToAccessAdminSettings() throws Exception {
        // Anti-pattern: Alice (ROLE_USER) guessing the admin URL
        mockMvc.perform(get("/api/lab/vulnerable/admin/settings")
                        .with(httpBasic("alice", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.setting").value("Global Application Configuration"));
                // Alice just accessed the admin panel!
    }

    @Test
    void secureAdminEndpoint_BlocksAliceFromAccessingAdminSettings() throws Exception {
        // Secure pattern: Alice correctly blocked
        mockMvc.perform(get("/api/lab/secure/admin/settings")
                        .with(httpBasic("alice", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void secureAdminEndpoint_AllowsAdminToAccessAdminSettings() throws Exception {
        // Secure pattern: Admin correctly allowed
        mockMvc.perform(get("/api/lab/secure/admin/settings")
                        .with(httpBasic("admin", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.setting").value("Global Application Configuration"));
    }
}
