package com.munishgarg.microsecurity.book1.ch3_grpc_tls_authz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldAllowWhenTlsAndTokenValid() {
        Map<String, Object> result = service.demo(Map.of("tlsEnabled", "true", "callTokenValid", "true"));

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
        assertEquals(15, result.get("riskScore"));
    }

    @Test
    void shouldDenyWhenTlsDisabled() {
        Map<String, Object> result = service.demo(Map.of("tlsEnabled", "false", "callTokenValid", "true"));

        assertNotNull(result);
        assertEquals("deny", result.get("controlDecision"));
        assertEquals(95, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch3-grpc-tls-authz", result.get("project"));
        assertEquals("TRANSPORT", result.get("controlFamily"));
    }
}
