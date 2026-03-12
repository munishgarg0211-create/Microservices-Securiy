package com.munishgarg.microsecurity.book1.ch4_jwt_validation_rotation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

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
    void shouldPermitInsecureEndpointWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/demo/insecure").param("actor", "eve").param("owner", "bob"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mode").value("insecure"))
                .andExpect(jsonPath("$.actor").value("eve"));
    }

    @Test
    void shouldDenySecureEndpointWithoutAuth() throws Exception {
        // Omitting JWT -> Expect 401 Unauthorized
        mockMvc.perform(get("/api/demo/secure").param("owner", "bob"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAllowSecureEndpointWithValidJwtOwnership() throws Exception {
        // Mocking a JWT where the subject (actor) is 'bob', matching the required owner 'bob'
        mockMvc.perform(get("/api/demo/secure").param("owner", "bob")
                        .with(jwt().jwt(jwt -> jwt.subject("bob"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mode").value("secure"))
                .andExpect(jsonPath("$.actor").value("bob"));
    }

    @Test
    void shouldDenySecureEndpointWithValidJwtButWrongOwnership() throws Exception {
        // Subject 'eve' trying to access 'bob', resulting in AccessDeniedException mapping to 403
        mockMvc.perform(get("/api/demo/secure").param("owner", "bob")
                        .with(jwt().jwt(jwt -> jwt.subject("eve"))))
                .andExpect(status().isForbidden());
    }
}
