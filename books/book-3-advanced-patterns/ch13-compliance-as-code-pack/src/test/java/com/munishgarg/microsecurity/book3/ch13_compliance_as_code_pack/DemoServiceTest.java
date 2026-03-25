package com.munishgarg.microsecurity.book3.ch13_compliance_as_code_pack;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldReturnProjectMetadataAndSecureDefaults() {
        Map<String, Object> result = service.demo(Map.of());
        assertNotNull(result);
        assertEquals("ch13-compliance-as-code-pack", result.get("project"));
        assertNotNull(result.get("expectedBehavior"));
    }
}