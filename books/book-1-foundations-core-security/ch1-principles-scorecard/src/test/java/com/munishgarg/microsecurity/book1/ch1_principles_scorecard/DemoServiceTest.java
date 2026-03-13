package com.munishgarg.microsecurity.book1.ch1_principles_scorecard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldScoreArchitectureAsCompliant() {
        Map<String, Object> result = service.demo(Map.of(
            "defenseInDepth", "true", 
            "leastPrivilege", "true", 
            "failSecurely", "true"
        ));

        assertNotNull(result);
        assertEquals("compliant", result.get("controlDecision"));
        assertEquals("100%", result.get("complianceScore"));
    }

    @Test
    void shouldScoreArchitectureAsNonCompliant() {
        Map<String, Object> result = service.demo(Map.of(
            "defenseInDepth", "false", 
            "leastPrivilege", "true", 
            "failSecurely", "true"
        ));

        assertNotNull(result);
        assertEquals("non-compliant", result.get("controlDecision"));
        assertEquals(85, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch1-principles-scorecard", result.get("project"));
        assertEquals("GOVERNANCE", result.get("controlFamily"));
    }
}
