package com.munishgarg.microsecurity.book1.ch3_kafka_security_baseline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldAllowWhenAllSecurityChecksPassed() {
        Map<String, Object> result = service.demo(Map.of(
            "sslEnabled", "true", 
            "saslAuthenticated", "true", 
            "aclAuthorized", "true"
        ));

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
        assertEquals(14, result.get("riskScore"));
    }

    @Test
    void shouldDenyWhenSslDisabled() {
        Map<String, Object> result = service.demo(Map.of(
            "sslEnabled", "false", 
            "saslAuthenticated", "true", 
            "aclAuthorized", "true"
        ));

        assertNotNull(result);
        assertEquals("deny", result.get("controlDecision"));
        assertEquals(96, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch3-kafka-security-baseline", result.get("project"));
        assertEquals("MESSAGING", result.get("controlFamily"));
    }
}
