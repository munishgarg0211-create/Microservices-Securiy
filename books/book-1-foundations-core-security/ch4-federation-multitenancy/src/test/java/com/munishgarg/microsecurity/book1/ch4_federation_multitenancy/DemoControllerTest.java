package com.munishgarg.microsecurity.book1.ch4_federation_multitenancy;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @MockBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void userWithAcmeTenantIdShouldAccessAcmeData() throws Exception {
        // Send a Mock JWT containing exactly the claim our custom converter is looking for
        mockMvc.perform(get("/api/demo/tenant/acme/data")
                        .with(jwt().jwt(jwt -> jwt
                                .claim("tenant_id", "ACME")
                                .subject("user-123"))
                                .authorities(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_TENANT_ACME"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tenant").value("acme"))
                .andExpect(jsonPath("$.accessedBy").value("user-123"));
    }

    @Test
    void userWithGlobexTenantIdShouldNotAccessAcmeData() throws Exception {
        // user tries to access 'acme' but their token says 'GLOBEX' -> 403 Forbidden
        mockMvc.perform(get("/api/demo/tenant/acme/data")
                        .with(jwt().jwt(jwt -> jwt
                                .claim("tenant_id", "GLOBEX")
                                .subject("user-456"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void userWithoutTenantIdShouldNotAccessTenantData() throws Exception {
        // Standard JWT without the custom claim
        mockMvc.perform(get("/api/demo/tenant/acme/data")
                        .with(jwt().jwt(jwt -> jwt.subject("user-789"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminUserShouldAccessSystemHealth() throws Exception {
        // Standard scope/role mapping for systemic access (acting as an Admin)
        mockMvc.perform(get("/api/demo/system/health")
                        .with(jwt().authorities(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("healthy"));
    }

    @Test
    void regularUserShouldNotAccessSystemHealth() throws Exception {
        mockMvc.perform(get("/api/demo/system/health")
                        .with(jwt().jwt(jwt -> jwt.subject("user-tenant-guy"))))
                .andExpect(status().isForbidden());
    }
    
    @Test
    void unauthenticatedRequestShouldFail() throws Exception {
        // Missing Bearer Token entirely -> 401 Unauthorized
        mockMvc.perform(get("/api/demo/tenant/acme/data"))
                .andExpect(status().isUnauthorized());
    }
}
