package com.munishgarg.microsecurity.book1.ch4_rbac_abac_demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void getAdminSettings_ShouldReturnSuccessPayload() {
        Map<String, String> result = service.getAdminSettings("test-admin");
        
        assertNotNull(result);
        assertEquals("success", result.get("status"));
        assertEquals("test-admin", result.get("accessedBy"));
    }

    @Test
    void getDocument_ShouldReturnDocumentPayload() {
        Map<String, String> result = service.getDocument("doc-99", "test-user");

        assertNotNull(result);
        assertEquals("success", result.get("status"));
        assertEquals("doc-99", result.get("documentId"));
        assertEquals("test-user", result.get("accessedBy"));
    }
}
