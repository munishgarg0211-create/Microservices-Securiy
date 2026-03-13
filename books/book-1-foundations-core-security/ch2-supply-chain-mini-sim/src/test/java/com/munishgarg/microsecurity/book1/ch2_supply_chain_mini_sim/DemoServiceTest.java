package com.munishgarg.microsecurity.book1.ch2_supply_chain_mini_sim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldAllowWhenSignedAndHasSbom() {
        Map<String, Object> result = service.demo(Map.of("imageSigned", "true", "hasSbom", "true"));

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
        assertEquals(18, result.get("riskScore"));
    }

    @Test
    void shouldDenyWhenUnsigned() {
        Map<String, Object> result = service.demo(Map.of("imageSigned", "false", "hasSbom", "true"));

        assertNotNull(result);
        assertEquals("deny", result.get("controlDecision"));
        assertEquals(94, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch2-supply-chain-mini-sim", result.get("project"));
        assertEquals("POLICY", result.get("controlFamily"));
    }
}
