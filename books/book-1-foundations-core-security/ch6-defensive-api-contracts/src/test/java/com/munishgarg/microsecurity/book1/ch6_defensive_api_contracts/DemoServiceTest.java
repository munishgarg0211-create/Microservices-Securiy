package com.munishgarg.microsecurity.book1.ch6_defensive_api_contracts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldReturnExpectedMetadata() {
        Map<String, String> result = service.demo();

        assertNotNull(result);
        assertEquals("ch6-defensive-api-contracts", result.get("project"));
        assertEquals("enabled", result.get("secureControl"));
        assertEquals("sample-ready", result.get("status"));
    }
}
