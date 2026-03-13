package com.munishgarg.microsecurity.book1.ch6_gateway_throttling_redis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import reactor.test.StepVerifier;

@SpringBootTest
class DemoServiceTest {

    @Autowired
    private DemoService service;

    @MockBean
    private ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;

    @MockBean
    private RedisConnectionFactory redisConnectionFactory;

    @Test
    void shouldReturnSecureResponse() {
        StepVerifier.create(service.demoSecure())
                .assertNext(result -> {
                    assertEquals("secure", result.get("mode"));
                    assertEquals("allow", result.get("controlDecision"));
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnInsecureResponse() {
        StepVerifier.create(service.demoInsecure())
                .assertNext(result -> {
                    assertEquals("insecure", result.get("mode"));
                    assertEquals(85, result.get("riskScore"));
                })
                .verifyComplete();
    }
}
