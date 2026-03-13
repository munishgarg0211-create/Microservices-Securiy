package com.munishgarg.microsecurity.book1.ch3_https_hardening;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldAllowModernTls() {
        Map<String, Object> result = service.demo(Map.of("tlsVersion", "1.2"));

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
        assertEquals(12, result.get("riskScore"));
    }

    @Test
    void shouldDenyWeakTls() {
        Map<String, Object> result = service.demo(Map.of("tlsVersion", "1.1"));

        assertNotNull(result);
        assertEquals("deny", result.get("controlDecision"));
        assertEquals(90, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch3-https-hardening", result.get("project"));
        assertEquals("TRANSPORT", result.get("controlFamily"));
    }
}
