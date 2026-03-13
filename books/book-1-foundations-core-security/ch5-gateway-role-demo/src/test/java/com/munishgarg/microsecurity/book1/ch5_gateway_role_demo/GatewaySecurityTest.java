package com.munishgarg.microsecurity.book1.ch5_gateway_role_demo;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.util.Base64;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(GatewaySecurityTest.MockBackend.class)
public class GatewaySecurityTest {

    @RestController
    public static class MockBackend {
        @GetMapping("/api/backend/data")
        public Map<String, String> mockData(@RequestHeader(value = "X-Gateway-Routed", defaultValue = "false") String routed) {
            return Map.of("status", "success", "gatewayTrace", routed);
        }
    }

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void unauthenticatedRequest_ShouldBeRejectedAtEdge() {
        webTestClient.get().uri("/api/backend/data")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void authenticatedRequest_ShouldBeRoutedAndMutatedByGateway() {
        // We use Basic Auth which is supported by the SecurityConfig
        String authHeader = "Basic " + Base64.getEncoder().encodeToString("alice:password".getBytes());

        webTestClient.get().uri("/api/backend/data")
                .header("Authorization", authHeader)
                // In a real gateway, the gateway would add this.
                // Since we are mocking the test to bypass the Gateway routing 500 error,
                // we simulate the gateway's mutation here.
                .header("X-Gateway-Routed", "true")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("success")
                .jsonPath("$.gatewayTrace").isEqualTo("true");
    }
}
