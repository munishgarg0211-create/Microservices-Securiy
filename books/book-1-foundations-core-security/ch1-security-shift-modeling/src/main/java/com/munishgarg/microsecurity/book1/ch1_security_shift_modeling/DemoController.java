package com.munishgarg.microsecurity.book1.ch1_security_shift_modeling;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates secure implementation for Security Shift-Left Modeling.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the secure Shift-Left modeling demo.
     * Use query parameters like 'designReview', 'threatModeling', and 'earlyMitigationRate' to simulate security maturity.
     * Example (Shielded): ?designReview=true&threatModeling=true&earlyMitigationRate=90
     * Example (Exposed): ?designReview=true&threatModeling=false
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
