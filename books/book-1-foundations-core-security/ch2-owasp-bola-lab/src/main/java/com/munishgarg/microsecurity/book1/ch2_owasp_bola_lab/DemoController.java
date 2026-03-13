package com.munishgarg.microsecurity.book1.ch2_owasp_bola_lab;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates the secure implementation for preventing BOLA vulnerabilities.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the secure BOLA demo.
     * Use query parameters 'actor' and 'resourceId' to simulate different access scenarios.
     * Example (Success): ?actor=bob&resourceId=bob_profile
     * Example (Deny): ?actor=alice&resourceId=bob_profile
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
