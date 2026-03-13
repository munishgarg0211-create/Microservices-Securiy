package com.munishgarg.microsecurity.book1.ch6_rate_algorithms_simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void tokenBucketShouldAllowBurst() {
        for (int i = 0; i < 10; i++) {
            Map<String, Object> result = service.demoSecure("token");
            assertEquals("allow", result.get("controlDecision"));
        }
    }

    @Test
    void leakyBucketShouldAllowInitialRequests() {
        Map<String, Object> result = service.demoSecure("leaky");
        assertEquals("allow", result.get("controlDecision"));
    }

    @Test
    void insecureModeShouldAlwaysAllow() {
        Map<String, Object> result = service.demoInsecure();
        assertEquals("allow", result.get("controlDecision"));
        assertEquals(95, result.get("riskScore"));
    }
}
