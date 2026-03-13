package com.munishgarg.microsecurity.book1.ch6_gateway_throttling_redis;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DemoService {

    public Mono<Map<String, Object>> demoSecure() {
        return buildResponse("secure", "allow", "Request passed gateway throttling checks.", 15);
    }

    public Mono<Map<String, Object>> demoInsecure() {
        return buildResponse("insecure", "allow", "Unguarded endpoint (simulated bypass).", 85);
    }

    private Mono<Map<String, Object>> buildResponse(String mode, String decision, String behavior, int risk) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", "ch6-gateway-throttling-redis");
        result.put("mode", mode);
        result.put("controlDecision", decision);
        result.put("expectedBehavior", behavior);
        result.put("riskScore", risk);
        return Mono.just(result);
    }
}
