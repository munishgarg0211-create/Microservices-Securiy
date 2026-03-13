package com.munishgarg.microsecurity.book1.ch5_request_validation_edge;

import jakarta.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class DemoController {

    /**
     * The @Valid annotation is CRITICAL here. 
     * It tells Spring to execute the JSR-380 validation rules defined inside PaymentRequest
     * BEFORE this method body even starts executing.
     * 
     * If validation fails, Spring throws a MethodArgumentNotValidException, which is intercepted
     * by our GlobalExceptionHandler. This prevents malicious data from reaching business logic.
     */
    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processPayment(@Valid @RequestBody PaymentRequest request) {
        
        // --- Imagine complex business logic or database calls here ---
        // We know for an absolute certainty that:
        // 1. userId is not blank.
        // 2. userEmail is a valid email string.
        // 3. amount is between 1 and 10000.
        // 4. description does not contain <script> tags or SQL injection characters like '.
        
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "Payment processed successfully. Input was strictly validated at the edge.");
        response.put("processedAmount", request.getAmount());
        
        return ResponseEntity.ok(response);
    }
}
