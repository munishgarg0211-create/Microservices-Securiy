package com.munishgarg.microsecurity.book1.ch6_dynamic_policy_engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private PolicyEngine policyEngine;
    private DemoService service;

    @BeforeEach
    void setUp() {
        policyEngine = new PolicyEngine();
        policyEngine.init(); // Manually trigger loading of policy.json
        service = new DemoService(policyEngine);
    }

    @Test
    void shouldPassWhenMetadataMeetsPolicy() {
        Map<String, Object> result = service.demo(Map.of(
            "imageSigned", "true", 
            "hasSbom", "true", 
            "criticalVulns", "0"
        ));

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
        // 0 risk from violations + 10 base risk for processed request
        assertEquals(10, result.get("riskScore"));
    }

    @Test
    void shouldBlockWhenMetadataViolatesPolicy() {
        // Violating imageSigned (impact 50)
        Map<String, Object> result = service.demo(Map.of(
            "imageSigned", "false", 
            "hasSbom", "true", 
            "criticalVulns", "0"
        ));

        assertNotNull(result);
        assertEquals("block", result.get("controlDecision"));
        // 50 risk from imageSigned + 10 base risk = 60
        assertEquals(60, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(Map.of());
        assertEquals("ch6-dynamic-policy-engine", result.get("project"));
        assertEquals("POLICY", result.get("controlFamily"));
    }
}
