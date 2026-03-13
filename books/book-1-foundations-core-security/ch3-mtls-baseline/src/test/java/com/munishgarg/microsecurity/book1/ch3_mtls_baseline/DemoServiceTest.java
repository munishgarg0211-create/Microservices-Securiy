package com.munishgarg.microsecurity.book1.ch3_mtls_baseline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldAllowTrustedmTLS() {
        Map<String, Object> result = service.demo(Map.of("clientCertPresent", "true", "certTrusted", "true"));

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
        assertEquals(10, result.get("riskScore"));
    }

    @Test
    void shouldDenyUntrustedmTLS() {
        Map<String, Object> result = service.demo(Map.of("clientCertPresent", "true", "certTrusted", "false"));

        assertNotNull(result);
        assertEquals("deny", result.get("controlDecision"));
        assertEquals(92, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch3-mtls-baseline", result.get("project"));
        assertEquals("TRANSPORT", result.get("controlFamily"));
    }
}
