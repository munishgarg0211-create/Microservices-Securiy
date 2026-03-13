package com.munishgarg.microsecurity.book1.ch6_rate_algorithms_simulator;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates Rate Limiting Algorithms: Token Bucket vs Leaky Bucket.
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
            @RequestParam(defaultValue = "token") String algo) {
        
        if ("insecure".equalsIgnoreCase(mode)) {
            return demoService.demoInsecure();
        }
        
        return demoService.demoSecure(algo);
    }
}
