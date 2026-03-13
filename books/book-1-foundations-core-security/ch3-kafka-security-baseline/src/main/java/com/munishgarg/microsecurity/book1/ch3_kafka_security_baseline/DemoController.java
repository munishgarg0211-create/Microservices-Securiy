package com.munishgarg.microsecurity.book1.ch3_kafka_security_baseline;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates secure implementation for Kafka messaging security.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the secure Kafka demo.
     * Use 'sslEnabled', 'saslAuthenticated', and 'aclAuthorized' query parameters to simulate Kafka access scenarios.
     * Example (Success): ?sslEnabled=true&saslAuthenticated=true&aclAuthorized=true
     * Example (Deny): ?sslEnabled=true&saslAuthenticated=true&aclAuthorized=false
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
