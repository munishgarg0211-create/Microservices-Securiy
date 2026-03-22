package com.munishgarg.microsecurity.book2.ch8_mesh_observability_enforcement;

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
        assertEquals("ch8-mesh-observability-enforcement", result.get("project"));
        assertEquals("unified-secure-baseline", result.get("securityModel"));
        assertEquals("production-ready", result.get("status"));
    }
}
