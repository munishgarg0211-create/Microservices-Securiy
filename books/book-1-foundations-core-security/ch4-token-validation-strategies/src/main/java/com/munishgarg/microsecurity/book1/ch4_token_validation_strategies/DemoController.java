package com.munishgarg.microsecurity.book1.ch4_token_validation_strategies;

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
     * This endpoint requires a valid JWT.
     * The token's validity is completely determined by the SecurityConfig's custom
     * Decoder stack, which includes our AudienceValidator.
     * If the token signature is valid, but the Audience is wrong, this code is never reached.
     */
    @GetMapping("/secure-data")
    public Map<String, String> getSecureData(Authentication authentication) {
        return demoService.getSecureData(authentication.getName());
    }
}
