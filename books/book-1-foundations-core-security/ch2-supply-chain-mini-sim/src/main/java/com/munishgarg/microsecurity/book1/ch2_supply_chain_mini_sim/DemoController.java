package com.munishgarg.microsecurity.book1.ch2_supply_chain_mini_sim;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates secure implementation for supply-chain artifact validation.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the secure supply chain demo.
     * Use 'imageSigned' and 'hasSbom' query parameters to simulate artifact states.
     * Example (Success): ?imageSigned=true&hasSbom=true
     * Example (Deny): ?imageSigned=false&hasSbom=true
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
