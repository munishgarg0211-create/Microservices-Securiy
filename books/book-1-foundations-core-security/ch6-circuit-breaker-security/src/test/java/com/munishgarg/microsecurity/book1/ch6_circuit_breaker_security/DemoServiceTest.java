package com.munishgarg.microsecurity.book1.ch6_circuit_breaker_security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DownstreamService downstreamService = new DownstreamService();
    private final DemoService service = new DemoService(downstreamService);

    @Test
    void shouldReturnSuccessWhenNotFailing() {
        Map<String, Object> result = service.demoSecure(false, 0);

        assertNotNull(result);
        assertEquals("ch6-circuit-breaker-security", result.get("project"));
        assertEquals("secure", result.get("mode"));
        assertEquals("allow", result.get("controlDecision"));
        assertEquals("Success from Downstream", result.get("data"));
    }

    @Test
    void shouldReturnInsecureFailureWhenFailing() {
        Map<String, Object> result = service.demoInsecure(true, 0);

        assertEquals("insecure", result.get("mode"));
        assertEquals("error", result.get("decision") != null ? result.get("decision") : result.get("controlDecision"));
        assertEquals(95, result.get("riskScore"));
    }
}
