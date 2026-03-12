package com.munishgarg.microsecurity.book1.ch4_auth_anti_patterns_lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void getUserData_ShouldReturnDataWithOwnerId() {
        Map<String, String> result = service.getUserData("alice");

        assertNotNull(result);
        assertEquals("success", result.get("status"));
        assertEquals("alice", result.get("owner"));
    }

    @Test
    void getAdminSettings_ShouldReturnGlobalConfig() {
        Map<String, String> result = service.getAdminSettings();

        assertNotNull(result);
        assertEquals("success", result.get("status"));
        assertEquals("Global Application Configuration", result.get("setting"));
    }
}
