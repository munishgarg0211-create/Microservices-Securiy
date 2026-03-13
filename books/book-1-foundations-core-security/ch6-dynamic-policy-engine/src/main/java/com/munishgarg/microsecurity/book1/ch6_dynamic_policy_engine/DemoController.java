package com.munishgarg.microsecurity.book1.ch6_dynamic_policy_engine;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates Dynamic Policy Engine: Externalized rule enforcement.
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
            @RequestParam Map<String, String> params) {
        
        if ("insecure".equalsIgnoreCase(mode)) {
            return demoService.demoInsecure(params);
        }
        
        return demoService.demoSecure(params);
    }
}
