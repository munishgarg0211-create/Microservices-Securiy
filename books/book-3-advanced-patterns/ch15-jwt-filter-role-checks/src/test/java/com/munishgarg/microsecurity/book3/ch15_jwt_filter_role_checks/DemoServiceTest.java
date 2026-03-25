package com.munishgarg.microsecurity.book3.ch15_jwt_filter_role_checks;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldReturnProjectMetadataAndSecureDefaults() {
        Map<String, Object> result = service.demo(Map.of());
        assertNotNull(result);
        assertEquals("ch15-jwt-filter-role-checks", result.get("project"));
        assertNotNull(result.get("expectedBehavior"));
    }
}