package com.munishgarg.microsecurity.book1.ch3_protocol_threat_comparison;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldApproveSecuregRPC() {
        Map<String, Object> result = service.demo(Map.of("protocol", "gRPC + TLS"));

        assertNotNull(result);
        assertEquals("approved-transport", result.get("controlDecision"));
        assertEquals(12, result.get("riskScore"));
    }

    @Test
    void shouldRejectPlainHttp() {
        Map<String, Object> result = service.demo(Map.of("protocol", "Plain HTTP"));

        assertNotNull(result);
        assertEquals("rejected-transport", result.get("controlDecision"));
        assertEquals(95, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch3-protocol-threat-comparison", result.get("project"));
        assertEquals("THREAT", result.get("controlFamily"));
    }
}
