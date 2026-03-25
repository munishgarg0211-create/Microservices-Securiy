package com.munishgarg.microsecurity.book3.ch14_agentic_self_healing;

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
        assertEquals("ch14-agentic-self-healing", result.get("project"));
        assertNotNull(result.get("expectedBehavior"));
    }
}