package com.munishgarg.microsecurity.book1.ch3_grpc_tls_authz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldReturnProjectMetadataAndSecureDefaults() {
        Map<String, Object> result = service.demo("secure", Map.of());

        assertNotNull(result);
        assertEquals("ch3-grpc-tls-authz", result.get("project"));
        assertEquals("enabled", result.get("secureControl"));
        assertEquals("sample-ready", result.get("status"));
        assertEquals("secure", result.get("mode"));
    }

    @Test
    void shouldDifferentiateSecureAndInsecureImpact() {
        Map<String, Object> secure = service.demo("secure", Map.of());
        Map<String, Object> insecure = service.demo("insecure", Map.of());

        assertEquals("secure", secure.get("mode"));
        assertEquals("insecure", insecure.get("mode"));
        assertNotEquals(secure.get("expectedBehavior"), insecure.get("expectedBehavior"));
    }
}
