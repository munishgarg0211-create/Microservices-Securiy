package com.munishgarg.microsecurity.book1.ch6_circuit_breaker_security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pure Unit Test for DemoService.
 * Uses a real DownstreamService instance to avoid Mockito instrumentation issues in Java 21 environments.
 */
class DemoServiceTest {

    private DownstreamService downstreamService;
    private DemoService demoService;

    @BeforeEach
    void setUp() {
        downstreamService = new DownstreamService();
        demoService = new DemoService(downstreamService);
    }

    @Test
    void shouldReturnSuccessResponse() {
        // We pass false for shouldFail to simulate a healthy downstream
        Map<String, Object> result = demoService.demo(false, 0);

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
        assertEquals("Success from Downstream", result.get("data"));
    }

    @Test
    void shouldReturnFallbackOnExceptionLogic() {
        // Directly test the fallback logic
        Map<String, Object> result = demoService.fallback(true, 0, new RuntimeException("Simulated Failure"));

        assertNotNull(result);
        assertEquals("fallback", result.get("controlDecision"));
        assertEquals("Cached/Static Data", result.get("data"));
        assertEquals(10, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = demoService.demo(false, 0);
        assertEquals("ch6-circuit-breaker-security", result.get("project"));
        assertEquals("RESILIENCE", result.get("controlFamily"));
    }
}
