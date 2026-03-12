package com.munishgarg.microsecurity.book1.ch4_spring_auth_starter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void getCorporateData_ShouldReturnStandardPayloadWithUsername() {
        Map<String, String> result = service.getCorporateData("alice");

        assertNotNull(result);
        assertEquals("success", result.get("status"));
        assertEquals("alice", result.get("accessedBy"));
    }
}
