package com.munishgarg.microsecurity.book1.ch6_circuit_breaker_security;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }
    // mode selects good practice (secure) vs intentionally bad practice (insecure).
    // params carries chapter-specific inputs so one endpoint can demo different controls.
    // Production copy/paste checklist:
    // 1) Treat request params as untrusted input and validate strictly.
    // 2) Use authenticated principal/claims from security context for auth decisions.
    // 3) Keep authorization/business decisions in service/policy layer, not in controllers.

    @GetMapping
    public Map<String, Object> getDemo(
            @RequestParam(defaultValue = "secure") String mode,
            @RequestParam(defaultValue = "false") boolean shouldFail,
            @RequestParam(defaultValue = "0") long delayMs) {
        
        if ("insecure".equalsIgnoreCase(mode)) {
            return demoService.demoInsecure(shouldFail, delayMs);
        }
        return demoService.demoSecure(shouldFail, delayMs);
    }
}
