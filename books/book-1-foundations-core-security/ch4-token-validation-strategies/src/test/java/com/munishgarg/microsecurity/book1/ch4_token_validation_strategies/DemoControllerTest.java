package com.munishgarg.microsecurity.book1.ch4_token_validation_strategies;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtDecoder jwtDecoder;

    @Test
    void validTokenWithCorrectAudience_ShouldReturn200() throws Exception {
        Jwt validJwt = Jwt.withTokenValue("valid-token")
                .header("alg", "none")
                .claim("sub", "alice-valid")
                .issuer("https://example.com/oauth2/default")
                .audience(List.of("api://demo-service"))
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        when(jwtDecoder.decode("valid-token")).thenReturn(validJwt);

        mockMvc.perform(get("/api/demo/secure-data")
                        .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessedBy").value("alice-valid"));
    }

    @Test
    void tokenWithWrongAudience_ShouldReturn401() throws Exception {
        when(jwtDecoder.decode("bad-audience-token")).thenThrow(
                new JwtValidationException("Invalid audience", List.of(new OAuth2Error("invalid_token")))
        );

        mockMvc.perform(get("/api/demo/secure-data")
                        .header("Authorization", "Bearer bad-audience-token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void tokenWithWrongIssuer_ShouldReturn401() throws Exception {
        when(jwtDecoder.decode("bad-issuer-token")).thenThrow(
                new JwtValidationException("Invalid issuer", List.of(new OAuth2Error("invalid_token")))
        );

        mockMvc.perform(get("/api/demo/secure-data")
                        .header("Authorization", "Bearer bad-issuer-token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void expiredToken_ShouldReturn401() throws Exception {
        when(jwtDecoder.decode("expired-token")).thenThrow(
                new JwtValidationException("Jwt expired", List.of(new OAuth2Error("invalid_token")))
        );

        mockMvc.perform(get("/api/demo/secure-data")
                        .header("Authorization", "Bearer expired-token"))
                .andExpect(status().isUnauthorized());
    }
    
    @Test
    void unauthenticatedRequest_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/demo/secure-data"))
                .andExpect(status().isUnauthorized());
    }
}
