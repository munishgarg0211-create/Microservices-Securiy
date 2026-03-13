package com.munishgarg.microsecurity.book1.ch1_role_based_learning_labs;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates secure implementation for Role-Based Access Control (RBAC).
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the secure RBAC demo.
     * Use query parameters like 'role' and 'action' to test permissions.
     * Example (Allow): ?role=Developer&action=read-code
     * Example (Deny): ?role=Developer&action=deploy-service
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
