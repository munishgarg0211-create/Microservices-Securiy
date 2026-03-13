package com.munishgarg.microsecurity.book1.ch6_gateway_throttling_redis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Pure Unit Test for DemoController.
 * Uses WebTestClient.bindToController() to bypass the full Spring context and auto-configuration,
 * avoiding environment-specific issues with Redis and Spring Cloud Gateway in tests.
 */
class DemoControllerTest {

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        // Use real service instance as it has no complex dependencies
        DemoService demoService = new DemoService();
        DemoController demoController = new DemoController(demoService);
        webTestClient = WebTestClient.bindToController(demoController).build();
    }

    @Test
    void shouldReturnAllowPayload() {
        webTestClient.get().uri("/api/demo")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.controlDecision").isEqualTo("allow")
                .jsonPath("$.riskScore").isEqualTo(10);
    }

    @Test
    void shouldIncludeStandardMetadata() {
        webTestClient.get().uri("/api/demo")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.project").isEqualTo("ch6-gateway-throttling-redis")
                .jsonPath("$.concept").isEqualTo("Perimeter Rate Limiting")
                .jsonPath("$.controlFamily").isEqualTo("RESILIENCE");
    }
}
