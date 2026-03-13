package com.munishgarg.microsecurity.book1.ch6_defensive_api_contracts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
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
    void shouldReturnSuccessWhenBudgetIsSufficient() {
        DeadlineContext.set(Instant.now().plusSeconds(5));
        Map<String, Object> result = service.processSecure(10);

        assertNotNull(result);
        assertEquals("allow", result.get("controlDecision"));
    }

    @Test
    void shouldReturnBlockWhenDeadlineIsExceeded() {
        DeadlineContext.set(Instant.now().minusSeconds(5));
        Map<String, Object> result = service.processSecure(0);

        assertEquals("block", result.get("controlDecision"));
        assertEquals(10, result.get("riskScore"));
    }

    @Test
    void shouldInsecureModeSucceedEvenWhenDeadlineIsExceeded() {
        DeadlineContext.set(Instant.now().minusSeconds(5));
        Map<String, Object> result = service.processInsecure(0);

        assertEquals("allow", result.get("controlDecision"));
        assertEquals(85, result.get("riskScore"));
    }
}
