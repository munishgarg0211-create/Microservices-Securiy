package com.munishgarg.microsecurity.book1.ch5_geo_ip_rate_controls;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * When the Resilience4j @RateLimiter gets tripped, it throws RequestNotPermitted.
     * We intercept it here to return a clean 429 Too Many Requests to the client.
     */
    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<Map<String, String>> handleRateLimitingException(RequestNotPermitted ex) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("error", "Rate limit exceeded. Please try again later.");
        
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
    }
}
