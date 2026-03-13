package com.munishgarg.microsecurity.book1.ch6_gateway_throttling_redis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldReturnResilientSuccess() {
        StepVerifier.create(service.demo())
                .assertNext(result -> {
                    assertNotNull(result);
                    assertEquals("allow", result.get("controlDecision"));
                    assertEquals(10, result.get("riskScore"));
                })
                .verifyComplete();
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        StepVerifier.create(service.demo())
                .assertNext(result -> {
                    assertEquals("ch6-gateway-throttling-redis", result.get("project"));
                    assertEquals("RESILIENCE", result.get("controlFamily"));
                })
                .verifyComplete();
    }
}
