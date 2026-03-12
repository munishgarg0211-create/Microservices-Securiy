package com.munishgarg.microsecurity.book1.ch4_auth_anti_patterns_lab;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public Map<String, String> getUserData(String userId) {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("status", "success");
        data.put("owner", userId);
        return data;
    }

    public Map<String, String> getAdminSettings() {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("status", "success");
        data.put("setting", "Global Application Configuration");
        return data;
    }
}
