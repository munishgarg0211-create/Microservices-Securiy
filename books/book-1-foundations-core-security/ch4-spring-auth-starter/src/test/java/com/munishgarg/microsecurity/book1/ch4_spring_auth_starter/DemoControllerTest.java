package com.munishgarg.microsecurity.book1.ch4_spring_auth_starter;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
    // We override the Issuer URI just for tests to match what mockMvc injects by default
    // or we can test that the custom properties are actually bound to the environment
    "corp.security.global.issuer-uri=https://test-idp.example.com"
})
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private GlobalSecurityProperties securityProperties;

    @Test
    void propertiesShouldBindCorrectlyFromEnvironment() {
        // Assert that Spring Boot correctly loaded our custom properties
        assert(securityProperties.isEnabled());
        assert("https://test-idp.example.com".equals(securityProperties.getIssuerUri()));
    }

    @Test
    void validToken_ShouldBeAcceptedByGlobalSecurityConfig() throws Exception {
        mockMvc.perform(get("/api/demo/corporate/data")
                        .with(jwt().jwt(jwt -> jwt.subject("corporate-user"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessedBy").value("corporate-user"));
    }

    @Test
    void unauthenticatedRequest_ShouldBeBlockedByGlobalSecurityConfig() throws Exception {
        mockMvc.perform(get("/api/demo/corporate/data"))
                .andExpect(status().isUnauthorized());
    }
}
