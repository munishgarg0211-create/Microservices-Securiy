package com.munishgarg.microsecurity.book1.ch6_defensive_api_contracts;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates defensive API contracts with deadline propagation.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the defensive API demo.
     * The deadline is typically set in the 'X-Deadline-Ms' header by an interceptor.
     * Use 'duration' param to simulate work time.
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam(defaultValue = "100") long duration) {
        return demoService.demo(duration);
    }
}
