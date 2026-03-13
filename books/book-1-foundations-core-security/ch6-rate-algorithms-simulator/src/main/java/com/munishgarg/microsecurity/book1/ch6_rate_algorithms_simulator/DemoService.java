package com.munishgarg.microsecurity.book1.ch6_rate_algorithms_simulator;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private final TokenBucket tokenBucket = new TokenBucket(10, 5); // 10 burst, 5/sec
    private final LeakyBucket leakyBucket = new LeakyBucket(10, 5); // 10 capacity, 5/sec

    public Map<String, Object> demoSecure(String algorithm) {
        boolean allowed;
        String algoName;

        if ("leaky".equalsIgnoreCase(algorithm)) {
            allowed = leakyBucket.tryConsume();
            algoName = "Leaky Bucket";
        } else {
            allowed = tokenBucket.tryConsume();
            algoName = "Token Bucket";
        }

        return buildResponse("secure", 
            allowed ? "allow" : "throttle", 
            allowed ? algoName + " passed request." : algoName + " capacity exceeded.", 
            allowed ? 10 : 90);
    }

    public Map<String, Object> demoInsecure() {
        return buildResponse("insecure", "allow", "No rate limiting applied.", 95);
    }

    private Map<String, Object> buildResponse(String mode, String decision, String behavior, int risk) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", "ch6-rate-algorithms-simulator");
        result.put("mode", mode);
        result.put("controlDecision", decision);
        result.put("expectedBehavior", behavior);
        result.put("riskScore", risk);
        return result;
    }
}
