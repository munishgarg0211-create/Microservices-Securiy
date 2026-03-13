package com.munishgarg.microsecurity.book1.ch6_circuit_breaker_security;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates resilient implementation using Circuit Breaker and Bulkhead.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the resilient service demo.
     * @param shouldFail Simulates a downstream failure.
     * @param delayMs Simulates downstream latency to trigger bulkhead/timeouts.
     */
    @GetMapping
    public Map<String, Object> getDemo(
            @RequestParam(defaultValue = "false") boolean shouldFail,
            @RequestParam(defaultValue = "0") long delayMs) {
        return demoService.demo(shouldFail, delayMs);
    }
}
