package com.munishgarg.microsecurity.book1.ch5_gateway_role_demo;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class DemoBackendController {

    @Bean
    public RouterFunction<ServerResponse> backendRoute() {
        return RouterFunctions.route()
                .GET("/internal/backend/data", request -> {
                    String routedHeader = request.headers().firstHeader("X-Gateway-Routed");
                    Map<String, String> data = new LinkedHashMap<>();
                    data.put("source", "Internal Backend Service");
                    data.put("status", "success");
                    data.put("message", "This highly sensitive data was protected at the network edge.");
                    data.put("gatewayTrace", routedHeader != null ? routedHeader : "Missing");
                    return ServerResponse.ok().bodyValue(data);
                })
                .build();
    }
}
