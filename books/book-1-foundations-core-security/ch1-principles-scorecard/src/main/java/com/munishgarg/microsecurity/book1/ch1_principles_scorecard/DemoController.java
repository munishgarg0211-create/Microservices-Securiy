package com.munishgarg.microsecurity.book1.ch1_principles_scorecard;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates secure implementation for Security Principles Scorecard.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the secure principles scorecard demo.
     * Use query parameters like 'defenseInDepth', 'leastPrivilege', and 'failSecurely' to simulate compliance.
     * Example (Compliant): ?defenseInDepth=true&leastPrivilege=true&failSecurely=true
     * Example (Non-compliant): ?defenseInDepth=true&leastPrivilege=false
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
