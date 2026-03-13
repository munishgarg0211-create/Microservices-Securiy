package com.munishgarg.microsecurity.book1.ch1_sdlc_security_pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldPassPipelineWhenAllStatesClean() {
        Map<String, Object> result = service.demo(Map.of("sastPassed", "true", "scaPassed", "true", "criticalVulns", "0"));

        assertNotNull(result);
        assertEquals("pass", result.get("controlDecision"));
        assertEquals(10, result.get("riskScore"));
    }

    @Test
    void shouldBlockPipelineWhenSCAStatusIsFailure() {
        Map<String, Object> result = service.demo(Map.of("sastPassed", "true", "scaPassed", "false", "criticalVulns", "0"));

        assertNotNull(result);
        assertEquals("block", result.get("controlDecision"));
        assertEquals(90, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch1-sdlc-security-pipeline", result.get("project"));
        assertEquals("POLICY", result.get("controlFamily"));
    }
}
