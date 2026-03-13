package com.munishgarg.microsecurity.book1.ch6_rate_algorithms_simulator;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates and simulates rate limiting algorithms.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the rate algorithm simulator demo.
     * @param algorithm Choice of 'token' or 'leaky'.
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam(defaultValue = "token") String algorithm) {
        return demoService.demo(algorithm);
    }
}
