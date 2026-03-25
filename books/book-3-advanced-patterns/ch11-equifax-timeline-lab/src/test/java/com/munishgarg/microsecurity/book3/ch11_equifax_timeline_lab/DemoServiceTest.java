package com.munishgarg.microsecurity.book3.ch11_equifax_timeline_lab;

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
        assertEquals("ch11-equifax-timeline-lab", result.get("project"));
        assertNotNull(result.get("expectedBehavior"));
    }
}