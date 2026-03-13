package com.munishgarg.microsecurity.book1.ch5_edge_observability;

import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    @GetMapping("/process")
    public Map<String, String> processData() {
        log.info("Executing core business logic. Observability context is automatically injected from the MDC.");
        
        Map<String, String> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "Data processed. Check the application logs for the automatically injected Trace ID.");
        return response;
    }

    @GetMapping("/demo")
    public Map<String, Object> getDemoPayload(@org.springframework.web.bind.annotation.RequestParam(defaultValue = "secure") String mode) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("project", "ch5-edge-observability");
        response.put("mode", mode);
        
        if ("insecure".equalsIgnoreCase(mode)) {
            response.put("expectedBehavior", "Actuator endpoints exposed without auth");
        } else {
            response.put("controlFamily", "OBSERVABILITY");
            response.put("controlDecision", "Secure Actuator via HTTP Basic");
        }
        return response;
    }
}
