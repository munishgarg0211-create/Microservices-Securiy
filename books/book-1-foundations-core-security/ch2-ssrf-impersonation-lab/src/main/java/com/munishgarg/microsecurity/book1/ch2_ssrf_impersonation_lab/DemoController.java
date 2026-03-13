package com.munishgarg.microsecurity.book1.ch2_ssrf_impersonation_lab;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates secure implementation for preventing SSRF vulnerabilities.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the secure SSRF demo.
     * Use 'url' query parameter to simulate targeting different resources.
     * Example (Success): ?url=https://public-api.example.com/data
     * Example (Deny): ?url=http://internal-metadata-service/secret
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
