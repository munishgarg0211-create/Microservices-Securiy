package com.munishgarg.microsecurity.book1.ch6_defensive_api_contracts;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates Defensive API Contracts: Deadline Propagation and Fail-Fast.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping
    public Map<String, Object> getDemo(
            @RequestParam(defaultValue = "secure") String mode,
            @RequestParam(defaultValue = "100") long workDurationMs) {
        
        if ("insecure".equalsIgnoreCase(mode)) {
            return demoService.processInsecure(workDurationMs);
        }
        
        return demoService.processSecure(workDurationMs);
    }
}
