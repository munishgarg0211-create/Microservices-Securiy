package com.munishgarg.microsecurity.book1.ch6_defensive_api_contracts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @AfterEach
    void tearDown() {
        DeadlineContext.clear();
    }

    @Test
    void shouldAllowWhenDeadlineIsSufficient() {
        DeadlineContext.set(1000); // 1 second deadline
        Map<String, Object> result = service.demo(100);

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
        assertEquals(15, result.get("riskScore"));
    }

    @Test
    void shouldFailFastWhenDeadlineAlreadyPassed() {
        DeadlineContext.set(-1); 
        Map<String, Object> result = service.demo(100);

        assertNotNull(result);
        assertEquals("block", result.get("controlDecision"));
        assertEquals(10, result.get("riskScore"));
    }

    @Test
    void shouldIncludeStandardsMetadata() {
        Map<String, Object> result = service.demo(0);
        assertEquals("ch6-defensive-api-contracts", result.get("project"));
        assertEquals("RESILIENCE", result.get("controlFamily"));
    }
}
