package com.munishgarg.microsecurity.book1.ch1_attack_surface_mapper;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates secure implementation for Attack Surface Mapping.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the secure Attack Surface mapping demo.
     * Use query parameters like 'endpoints', 'exposed', and 'mfaEnforced' to simulate topology.
     * Example (Optimized): ?endpoints=10&exposed=1&mfaEnforced=true
     * Example (Over-exposed): ?endpoints=10&exposed=5&mfaEnforced=false
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
