package com.munishgarg.microsecurity.book1.ch3_mtls_baseline;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates secure implementation for mutual TLS (mTLS) authentication.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the secure mTLS demo.
     * Use 'clientCertPresent' and 'certTrusted' query parameters to simulate mTLS handshake outcomes.
     * Example (Success): ?clientCertPresent=true&certTrusted=true
     * Example (Deny): ?clientCertPresent=true&certTrusted=false
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
