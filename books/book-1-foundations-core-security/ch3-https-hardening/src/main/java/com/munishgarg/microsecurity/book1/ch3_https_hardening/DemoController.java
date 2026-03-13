package com.munishgarg.microsecurity.book1.ch3_https_hardening;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates secure implementation for HTTPS/TLS hardening.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the secure HTTPS demo.
     * Use 'tlsVersion' query parameter to simulate client connection attempts.
     * Example (Success): ?tlsVersion=1.2
     * Example (Deny): ?tlsVersion=1.0
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
