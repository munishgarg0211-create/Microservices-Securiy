package com.munishgarg.microsecurity.book1.ch4_spring_auth_starter;

import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * This endpoint is automatically secured by the Global Security Starter.
     * The developer didn't have to write any boilerplate SecurityConfig
     * to ensure this endpoint requires a valid JWT verified against the Platform IdP.
     */
    @GetMapping("/corporate/data")
    public Map<String, String> getCorporateData(Authentication authentication) {
        return demoService.getCorporateData(authentication.getName());
    }
}
