package com.munishgarg.microsecurity.book1.ch1_attack_surface_mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldEvaluateSurfaceOptimized() {
        Map<String, Object> result = service.demo(Map.of("endpoints", "10", "exposed", "1", "mfaEnforced", "true"));

        assertNotNull(result);
        assertEquals("optimized", result.get("controlDecision"));
        assertEquals(18, result.get("riskScore"));
    }

    @Test
    void shouldEvaluateSurfaceOverExposed() {
        Map<String, Object> result = service.demo(Map.of("endpoints", "10", "exposed", "5", "mfaEnforced", "false"));

        assertNotNull(result);
        assertEquals("over-exposed", result.get("controlDecision"));
        assertEquals(88, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch1-attack-surface-mapper", result.get("project"));
        assertEquals("THREAT", result.get("controlFamily"));
    }
}
