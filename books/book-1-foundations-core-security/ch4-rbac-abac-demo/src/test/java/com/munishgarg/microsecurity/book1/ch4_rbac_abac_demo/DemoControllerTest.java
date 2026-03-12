package com.munishgarg.microsecurity.book1.ch4_rbac_abac_demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void adminCanAccessAdminSettings() throws Exception {
        mockMvc.perform(get("/api/demo/admin-settings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.accessedBy").value("admin"));
    }

    @Test
    @WithMockUser(username = "alice", roles = {"USER"})
    void regularUserCannotAccessAdminSettings() throws Exception {
        mockMvc.perform(get("/api/demo/admin-settings"))
                .andExpect(status().isForbidden()); // 403 Forbidden
    }

    @Test
    @WithMockUser(username = "alice", roles = {"USER"})
    void ownerCanAccessTheirOwnDocument() throws Exception {
        // DocumentSecurityTracker mock data says 'alice' owns 'doc-1'
        mockMvc.perform(get("/api/demo/documents/doc-1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "bob", roles = {"USER"})
    void nonOwnerCannotAccessDocument() throws Exception {
        // 'bob' tries to access 'doc-1' which is owned by 'alice'
        mockMvc.perform(get("/api/demo/documents/doc-1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "superadmin", roles = {"ADMIN"})
    void adminCanAccessAnyDocumentDespiteNotBeingOwner() throws Exception {
        // 'superadmin' doesn't own doc-1, but has ROLE_ADMIN
        mockMvc.perform(get("/api/demo/documents/doc-1"))
                .andExpect(status().isOk());
    }
    
    @Test
    void unauthenticatedUserCannotAccessAnything() throws Exception {
        // Spring Security by default rejects anonymous users for @PreAuthorize endpoints 
        // if they don't have the context, or it throws AuthenticationCredentialsNotFoundException
        mockMvc.perform(get("/api/demo/documents/doc-1"))
                .andExpect(status().isUnauthorized()); // 401
    }
}
