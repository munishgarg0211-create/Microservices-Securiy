package com.munishgarg.microsecurity.book1.ch5_geo_ip_rate_controls;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    /**
     * The @RateLimiter annotation links this endpoint directly to the "api-limiter"
     * configuration defined in application.yml.
     * 
     * If more than 5 requests arrive within the 10-second window, Resilience4j 
     * throws a RequestNotPermitted exception, short-circuiting this method entirely.
     */
    @GetMapping
    @RateLimiter(name = "api-limiter")
    public Map<String, Object> getProtectedResource(@RequestParam(required = false) String mode) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("project", "ch5-geo-ip-rate-controls");
        data.put("status", "success");
        
        if ("insecure".equalsIgnoreCase(mode)) {
            data.put("mode", "insecure");
            data.put("expectedBehavior", "Vulnerable to flooding and regional attacks");
        } else {
            data.put("mode", "secure");
            data.put("controlFamily", "Perimeter Protection");
            data.put("controlDecision", "Allow (Passed Geo-IP and Rate Limiter)");
            data.put("message", "Request permitted. You passed both the Geo-IP filter and the Rate Limiter.");
        }
        
        return data;
    }
}
