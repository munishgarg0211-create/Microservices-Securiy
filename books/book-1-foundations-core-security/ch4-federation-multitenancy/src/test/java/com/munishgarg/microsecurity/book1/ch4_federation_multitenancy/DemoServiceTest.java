package com.munishgarg.microsecurity.book1.ch4_federation_multitenancy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void getTenantData_ShouldReturnIsolatedData() {
        Map<String, String> result = service.getTenantData("ACME", "test-user");

        assertNotNull(result);
        assertEquals("success", result.get("status"));
        assertEquals("ACME", result.get("tenant"));
        assertEquals("test-user", result.get("accessedBy"));
    }

    @Test
    void getSystemHealth_ShouldReturnHealthMetrics() {
        Map<String, String> result = service.getSystemHealth("admin-user");

        assertNotNull(result);
        assertEquals("healthy", result.get("status"));
        assertEquals("admin-user", result.get("accessedBy"));
    }
}
