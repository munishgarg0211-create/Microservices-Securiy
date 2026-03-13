package com.munishgarg.microsecurity.book1.ch2_ssrf_impersonation_lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldDenyInternalUrl() {
        Map<String, Object> result = service.demo(Map.of("url", "http://internal-service/api"));

        assertNotNull(result);
        assertEquals("deny", result.get("controlDecision"));
        assertEquals(true, result.get("isInternalResource"));
        assertEquals(98, result.get("riskScore"));
    }

    @Test
    void shouldAllowPublicUrl() {
        Map<String, Object> result = service.demo(Map.of("url", "https://google.com"));

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
        assertEquals(false, result.get("isInternalResource"));
        assertEquals(15, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch2-ssrf-impersonation-lab", result.get("project"));
        assertEquals("THREAT", result.get("controlFamily"));
    }
}
