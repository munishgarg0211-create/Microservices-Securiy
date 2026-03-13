package com.munishgarg.microsecurity.book1.ch3_grpc_tls_authz;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates secure implementation for gRPC transport and call credentials.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the secure gRPC demo.
     * Use 'tlsEnabled' and 'callTokenValid' query parameters to simulate connection/call states.
     * Example (Success): ?tlsEnabled=true&callTokenValid=true
     * Example (Deny): ?tlsEnabled=true&callTokenValid=false
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
