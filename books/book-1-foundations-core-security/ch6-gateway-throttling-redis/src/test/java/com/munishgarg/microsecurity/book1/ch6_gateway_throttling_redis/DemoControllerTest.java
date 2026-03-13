package com.munishgarg.microsecurity.book1.ch6_gateway_throttling_redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;

    @MockBean
    private RedisConnectionFactory redisConnectionFactory;

    @Test
    void shouldReturnSecurePayload() {
        webTestClient.get().uri("/api/demo?mode=secure")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.project").isEqualTo("ch6-gateway-throttling-redis")
                .jsonPath("$.mode").isEqualTo("secure")
                .jsonPath("$.controlDecision").isEqualTo("allow");
    }

    @Test
    void shouldReturnInsecurePayload() {
        webTestClient.get().uri("/api/demo?mode=insecure")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.mode").isEqualTo("insecure")
                .jsonPath("$.riskScore").isEqualTo(85);
    }
}
