package com.munishgarg.microsecurity.book1.ch6_gateway_throttling_redis;

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
        assertEquals("ch6-gateway-throttling-redis", result.get("project"));
        assertEquals("enabled", result.get("secureControl"));
        assertEquals("sample-ready", result.get("status"));
    }
}
