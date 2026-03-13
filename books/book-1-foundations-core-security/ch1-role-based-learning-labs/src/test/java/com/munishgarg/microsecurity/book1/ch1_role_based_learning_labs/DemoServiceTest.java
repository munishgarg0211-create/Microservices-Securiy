package com.munishgarg.microsecurity.book1.ch1_role_based_learning_labs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldAllowCorrectRoleAction() {
        Map<String, Object> result = service.demo(Map.of("role", "SRE", "action", "deploy-service"));

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
        assertEquals(12, result.get("riskScore"));
    }

    @Test
    void shouldDenyIncorrectRoleAction() {
        Map<String, Object> result = service.demo(Map.of("role", "SRE", "action", "write-code"));

        assertNotNull(result);
        assertEquals("deny", result.get("controlDecision"));
        assertEquals(94, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch1-role-based-learning-labs", result.get("project"));
        assertEquals("AUTHZ", result.get("controlFamily"));
    }
}
