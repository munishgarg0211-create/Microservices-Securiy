package com.munishgarg.microsecurity.book3.ch15_istio_policy_recipe;

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
        assertEquals("ch15-istio-policy-recipe", result.get("project"));
        assertNotNull(result.get("expectedBehavior"));
    }
}