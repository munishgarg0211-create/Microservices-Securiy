package com.munishgarg.microsecurity.book1.ch6_dynamic_policy_engine;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates secure implementation for Dynamic Policy Evaluation.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the secure dynamic policy demo.
     * Use query parameters like 'imageSigned', 'hasSbom', and 'criticalVulns' to simulate artifact metadata.
     * Example (Allow): ?imageSigned=true&hasSbom=true&criticalVulns=0
     * Example (Block): ?imageSigned=true&hasSbom=false
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
