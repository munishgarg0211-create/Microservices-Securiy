package com.munishgarg.microsecurity.book1.ch4_token_validation_strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void getSecureData_ShouldReturnStandardPayloadWithUsername() {
        Map<String, String> result = service.getSecureData("alice");

        assertNotNull(result);
        assertEquals("success", result.get("status"));
        assertEquals("alice", result.get("accessedBy"));
    }
}
