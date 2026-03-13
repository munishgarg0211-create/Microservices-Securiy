package com.munishgarg.microsecurity.book1.ch5_gateway_role_demo;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class DemoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldServeSecureDemoPayload() {
        webTestClient.mutateWith(mockUser("alice").roles("USER")) // bypass security constraint with a mock auth
                .get().uri("/api/demo")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.project").isEqualTo("ch5-gateway-role-demo")
                .jsonPath("$.mode").isEqualTo("secure")
                .jsonPath("$.controlFamily").isNotEmpty()
                .jsonPath("$.controlDecision").isNotEmpty();
    }

    @Test
    void shouldServeInsecureDemoPayload() {
        webTestClient.mutateWith(mockUser("alice").roles("USER"))
                .get().uri(uriBuilder -> uriBuilder.path("/api/demo").queryParam("mode", "insecure").build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.project").isEqualTo("ch5-gateway-role-demo")
                .jsonPath("$.mode").isEqualTo("insecure")
                .jsonPath("$.expectedBehavior").isNotEmpty();
    }
}
