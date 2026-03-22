package com.munishgarg.microsecurity.book2.bonus_container_hardening_lab;

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
        assertEquals("bonus-container-hardening-lab", result.get("project"));
        assertEquals("enabled", result.get("secureControl"));
        assertEquals("sample-ready", result.get("status"));
        assertEquals("unified-secure-baseline", result.get("securityModel"));
    }


}
