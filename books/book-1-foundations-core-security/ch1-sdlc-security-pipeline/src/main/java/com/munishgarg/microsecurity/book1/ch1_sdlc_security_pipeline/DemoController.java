package com.munishgarg.microsecurity.book1.ch1_sdlc_security_pipeline;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates secure implementation for SDLC Security Pipeline gates.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the secure SDLC pipeline demo.
     * Use query parameters like 'sastPassed', 'scaPassed', and 'criticalVulns' to simulate pipeline outcomes.
     * Example (Pass): ?sastPassed=true&scaPassed=true&criticalVulns=0
     * Example (Block): ?sastPassed=true&scaPassed=true&criticalVulns=2
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
