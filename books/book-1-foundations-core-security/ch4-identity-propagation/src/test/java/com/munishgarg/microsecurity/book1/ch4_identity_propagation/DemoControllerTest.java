package com.munishgarg.microsecurity.book1.ch4_identity_propagation;

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
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.when;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DemoService demoService;

    @MockBean
    private JwtDecoder jwtDecoder;

    @Test
    void requestingUserProfileWithJwt_ShouldYieldOk() throws Exception {
        // Mock the service layer reaction
        when(demoService.fetchDownstreamProfile()).thenReturn(
                Mono.just(Map.of("status", "success", "authenticatedUserAtDownstream", "alice"))
        );

        mockMvc.perform(get("/api/demo/user/profile")
                        .with(jwt().jwt(jwt -> jwt.subject("alice"))))
                .andExpect(status().isOk());
    }

    @Test
    void requestingDownstreamApiWithJwt_ShouldYieldOkAndExtractIdentity() throws Exception {
        // When the internal API is hit directly with a token
        when(demoService.getMockedDownstreamData()).thenReturn(
                Map.of("status", "success", "authenticatedUserAtDownstream", "bob-downstream")
        );

        mockMvc.perform(get("/api/demo/internal/downstream-api/profile")
                        .with(jwt().jwt(jwt -> jwt.subject("bob-downstream"))))
                .andExpect(status().isOk());
    }

    @Test
    void unauthenticatedRequestToUserProfile_ShouldFail401() throws Exception {
        mockMvc.perform(get("/api/demo/user/profile"))
                .andExpect(status().isUnauthorized());
    }
}
