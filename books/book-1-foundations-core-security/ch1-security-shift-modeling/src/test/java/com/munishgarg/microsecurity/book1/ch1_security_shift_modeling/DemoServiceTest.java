package com.munishgarg.microsecurity.book1.ch1_security_shift_modeling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldEvaluateModelingAsShielded() {
        Map<String, Object> result = service.demo(Map.of(
            "designReview", "true", 
            "threatModeling", "true", 
            "earlyMitigationRate", "85"
        ));

        assertNotNull(result);
        assertEquals("shielded", result.get("controlDecision"));
        assertEquals(12, result.get("riskScore"));
    }

    @Test
    void shouldEvaluateModelingAsExposedWhenRateIsLow() {
        Map<String, Object> result = service.demo(Map.of(
            "designReview", "true", 
            "threatModeling", "true", 
            "earlyMitigationRate", "70"
        ));

        assertNotNull(result);
        assertEquals("exposed", result.get("controlDecision"));
        assertEquals(88, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch1-security-shift-modeling", result.get("project"));
        assertEquals("THREAT", result.get("controlFamily"));
    }
}
