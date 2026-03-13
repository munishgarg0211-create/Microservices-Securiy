package com.munishgarg.microsecurity.book1.ch6_dynamic_policy_engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoServiceTest {

    @Autowired
    private DemoService service;

    @Test
    void shouldPassWhenRulesAreMet() {
        Map<String, String> params = Map.of(
            "imageSigned", "true",
            "hasSbom", "true",
            "criticalVulns", "0"
        );
        Map<String, Object> result = service.demoSecure(params);

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
    }

    @Test
    void shouldReportViolationsWhenRulesFail() {
        Map<String, String> params = Map.of(
            "imageSigned", "false",
            "hasSbom", "false"
        );
        Map<String, Object> result = service.demoSecure(params);

        assertEquals("block", result.get("controlDecision"));
        assertTrue(result.get("riskScore") instanceof Integer);
        assertTrue((Integer) result.get("riskScore") > 0);
    }
}
