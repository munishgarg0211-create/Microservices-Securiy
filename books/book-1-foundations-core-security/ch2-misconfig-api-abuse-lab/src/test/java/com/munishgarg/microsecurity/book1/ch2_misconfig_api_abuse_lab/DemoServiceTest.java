package com.munishgarg.microsecurity.book1.ch2_misconfig_api_abuse_lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldAllowWhenAttemptsUnderThreshold() {
        Map<String, Object> result = service.demo(Map.of("attempts", "3"));

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
        assertEquals(10, result.get("riskScore"));
    }

    @Test
    void shouldThrottleWhenAttemptsExceedThreshold() {
        Map<String, Object> result = service.demo(Map.of("attempts", "10"));

        assertNotNull(result);
        assertEquals("throttle", result.get("controlDecision"));
        assertEquals(92, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch2-misconfig-api-abuse-lab", result.get("project"));
        assertEquals("THREAT", result.get("controlFamily"));
    }
}
