package com.munishgarg.microsecurity.book1.ch3_protocol_threat_comparison;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates security comparison between different communication protocols.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the secure protocol comparison demo.
     * Use 'protocol' query parameter to evaluate different transport methods.
     * Example (Success): ?protocol=gRPC%20+%20TLS
     * Example (Deny): ?protocol=Plain%20HTTP
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
