package com.munishgarg.microsecurity.book1.ch5_perimeter_threat_mitigation;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public Map<String, Object> getDemoData(String mode) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("project", "ch5-perimeter-threat-mitigation");
        
        if ("insecure".equalsIgnoreCase(mode)) {
            data.put("mode", "insecure");
            data.put("expectedBehavior", "Browser lacks defense-in-depth headers like CSP/HSTS/X-Frame-Options.");
        } else {
            data.put("mode", "secure");
            data.put("controlFamily", "THREAT");
            data.put("controlDecision", "Enforce strict security headers (CSP, HSTS, DENY) and restrictive CORS at the perimeter.");
        }
        
        return data;
    }
}
