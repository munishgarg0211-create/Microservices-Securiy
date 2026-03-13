package com.munishgarg.microsecurity.book1.ch2_stride_control_mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldMapThreatToCorrectControl() {
        Map<String, Object> result = service.demo(Map.of("threat", "Tampering"));

        assertNotNull(result);
        assertEquals("mitigated", result.get("controlDecision"));
        assertEquals("Integrity / Signing (HMAC, Digital Signatures)", result.get("mappedControl"));
        assertEquals(15, result.get("riskScore"));
    }

    @Test
    void shouldHandleInvalidThreat() {
        Map<String, Object> result = service.demo(Map.of("threat", "Unknown"));

        assertNotNull(result);
        assertEquals("exposed", result.get("controlDecision"));
        assertEquals(85, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch2-stride-control-mapper", result.get("project"));
        assertEquals("THREAT", result.get("controlFamily"));
    }
}
