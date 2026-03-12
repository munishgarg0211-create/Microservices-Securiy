package com.munishgarg.microsecurity.book1.ch4_token_validation_strategies;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public Map<String, String> getSecureData(String username) {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("status", "success");
        data.put("accessedBy", username);
        data.put("message", "You routed through all custom JWT validation rules, including Audience and Issuer.");
        return data;
    }
}
