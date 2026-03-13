package com.munishgarg.microsecurity.book1.ch2_owasp_bola_lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldReturnSecureDecisionForOwner() {
        Map<String, Object> result = service.demo(Map.of("actor", "bob", "resourceId", "bob_profile"));

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
        assertEquals(10, result.get("riskScore"));
    }

    @Test
    void shouldReturnDenyDecisionForMismatch() {
        Map<String, Object> result = service.demo(Map.of("actor", "alice", "resourceId", "bob_profile"));

        assertNotNull(result);
        assertEquals("deny", result.get("controlDecision"));
        assertEquals(95, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch2-owasp-bola-lab", result.get("project"));
        assertEquals("AUTHZ", result.get("controlFamily"));
    }
}
