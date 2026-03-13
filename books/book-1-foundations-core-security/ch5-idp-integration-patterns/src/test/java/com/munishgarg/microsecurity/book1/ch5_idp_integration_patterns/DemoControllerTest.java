package com.munishgarg.microsecurity.book1.ch5_idp_integration_patterns;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ImportAutoConfiguration(exclude = org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class)
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @org.springframework.boot.test.mock.mockito.MockBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @Test
    void unauthenticatedRequest_ShouldRedirectToIdPLogin() throws Exception {
        // When completely unauthenticated, Spring Security should issue a 302 Redirect 
        // to the OAuth2 provider selection page (or directly to the IdP if only one is configured).
        mockMvc.perform(get("/api/demo/me"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void authenticatedOidcRequest_ShouldReturnFederatedProfile() throws Exception {
        // We simulate a successful OIDC login from a provider (e.g., Google)
        // Spring Security Test provides 'oidcLogin()' to mock the resulting SecurityContext.
        mockMvc.perform(get("/api/demo/me")
                        .with(oidcLogin()
                                .idToken(token -> token
                                        .subject("subject-12345")
                                        .claim("email", "test.user@example.com")
                                        .claim("name", "Test User"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.subject").value("subject-12345"))
                .andExpect(jsonPath("$.fullName").value("Test User"))
                .andExpect(jsonPath("$.email").value("test.user@example.com"));
    }
}
