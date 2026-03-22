package com.munishgarg.microsecurity.book2.ch7_k8s_rbac_isolation;

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
        assertEquals("ch7-k8s-rbac-isolation", result.get("project"));
        assertEquals("unified-secure-baseline", result.get("securityModel"));
    }
}
