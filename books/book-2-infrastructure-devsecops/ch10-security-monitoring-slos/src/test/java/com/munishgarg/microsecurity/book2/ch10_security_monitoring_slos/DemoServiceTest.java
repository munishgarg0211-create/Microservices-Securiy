package com.munishgarg.microsecurity.book2.ch10_security_monitoring_slos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldReturnProjectMetadataAndSecureDefaults() {
        Map<String, Object> result = service.demo(Map.of());

        assertNotNull(result);
        assertEquals("ch10-security-monitoring-slos", result.get("project"));
        assertEquals("unified-secure-baseline", result.get("securityModel"));
        assertEquals("production-ready", result.get("status"));
    }
}
