package com.munishgarg.microsecurity.book1.ch6_rate_algorithms_simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldAllowTokenBucketConsumption() {
        Map<String, Object> result = service.demo("token");

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
        assertEquals(15, result.get("riskScore"));
    }

    @Test
    void shouldAllowLeakyBucketConsumption() {
        Map<String, Object> result = service.demo("leaky");

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
        assertEquals(15, result.get("riskScore"));
    }

    @Test
    void shouldThottleWhenTokenBucketEmpty() {
        // Drain tokens
        for(int i=0; i<10; i++) {
            service.demo("token");
        }
        
        Map<String, Object> result = service.demo("token");
        assertEquals("throttle", result.get("controlDecision"));
        assertEquals(88, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo("token");
        assertEquals("ch6-rate-algorithms-simulator", result.get("project"));
        assertEquals("RESILIENCE", result.get("controlFamily"));
    }
}
