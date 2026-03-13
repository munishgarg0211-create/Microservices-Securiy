package com.munishgarg.microsecurity.book1.ch6_gateway_throttling_redis;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping
    public Mono<Map<String, Object>> getDemo(
            @RequestParam(defaultValue = "secure") String mode) {
        
        if ("insecure".equalsIgnoreCase(mode)) {
            return demoService.demoInsecure();
        }
        
        return demoService.demoSecure();
    }
}
