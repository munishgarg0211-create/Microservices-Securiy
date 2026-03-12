package com.munishgarg.microsecurity.book1.ch4_identity_propagation;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * An externally facing endpoint that internally calls a downstream mock system.
     * The incoming request to this endpoint will have a Bearer token. 
     * We use WebClient (inside DemoService) to seamlessly forward that token.
     */
    @GetMapping("/user/profile")
    public Mono<Map<String, String>> getUserProfile() {
        return demoService.fetchDownstreamProfile();
    }

    /**
     * A mock endpoint representing a backend downstream microservice.
     * In a real deployment, this would be a completely separate application.
     */
    @GetMapping("/internal/downstream-api/profile")
    public Map<String, String> mockDownstreamApi() {
        return demoService.getMockedDownstreamData();
    }
}
