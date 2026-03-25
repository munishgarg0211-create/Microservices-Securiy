package com.munishgarg.microsecurity.book3.ch14_ethical_risk_checklist;

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
        assertEquals("ch14-ethical-risk-checklist", result.get("project"));
        assertNotNull(result.get("expectedBehavior"));
    }
}