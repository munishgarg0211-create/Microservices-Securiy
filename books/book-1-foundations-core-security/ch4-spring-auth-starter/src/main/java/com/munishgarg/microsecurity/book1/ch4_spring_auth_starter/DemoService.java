package com.munishgarg.microsecurity.book1.ch4_spring_auth_starter;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public Map<String, String> getCorporateData(String username) {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("status", "success");
        data.put("accessedBy", username);
        data.put("message", "Request authenticated by the globally shared Spring Auth Starter configuration.");
        return data;
    }
}
